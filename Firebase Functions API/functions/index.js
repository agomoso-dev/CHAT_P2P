/**
 * Import function triggers from their respective submodules:
 *
 * const {onCall} = require("firebase-functions/v2/https");
 * const {onDocumentWritten} = require("firebase-functions/v2/firestore");
 *
 * See a full list of supported triggers at https://firebase.google.com/docs/functions
 */

// Create and deploy your first functions
// https://firebase.google.com/docs/functions/get-started

// The Cloud Functions for Firebase SDK to create Cloud Functions and set up triggers.
const functions = require('firebase-functions/v1');

// The Firebase Admin SDK to access Firestore.
const admin = require("firebase-admin");
admin.initializeApp();

/** Servicios Firebase */
const db = admin.firestore();      // Firestore

const storage = admin.storage();   // Storage
const bucket = storage.bucket();   // Storage Bucket

/**
 * Agrega un nuevo usuario
 */
exports.addUser = functions.https.onRequest(async (req, res) => {
    if (req.method !== 'POST') {
        return res.status(405).json({ success: false, message: 'Método no permitido. Usa POST', data: {} });
    }

    const { userId, username, ip, port, avatar } = req.body;
    if (!userId || !username || !ip || !port) {
        return res.status(400).json({ success: false, message: 'Faltan datos obligatorios', data: {} });
    }

    try {
        let avatarUrl = null;

        if (avatar && avatar.imageData) {
            const avatarFile = bucket.file(`avatars/${userId}.jpg`);
            const avatarBuffer = Buffer.from(avatar.imageData, 'base64');

            await avatarFile.save(avatarBuffer, {
                metadata: { contentType: 'image/jpeg' },
            });

            await avatarFile.makePublic();
            avatarUrl = avatarFile.publicUrl();
        }

        const userData = {
            username,
            ip,
            port,
            contacts: [],
            lastUpdated: admin.firestore.FieldValue.serverTimestamp()
        };

        if (avatarUrl) {
            userData.avatar = {
                localPath: null,
                storageUrl: avatarUrl,
                imageData: null
            };
        }

        await db.collection('users').doc(userId).set(userData);

        const responseData = {
            userId,
            username,
            ip,
            port,
            avatar: avatarUrl ? {
                localPath: null,
                storageUrl: avatarUrl,
                imageData: null
            } : null
        };

        return res.status(200).json({
            success: true,
            message: 'Usuario agregado exitosamente',
            data: responseData
        });

    } catch (error) {
        return res.status(500).json({ success: false, message: 'Error al guardar usuario', error: error.message, data: {} });
    }
});

/**
 * Actualiza un usuario ya existente
 */
exports.updateUser = functions.https.onRequest(async (req, res) => {
    if (req.method !== 'PATCH') {
        return res.status(405).json({
            success: false,
            message: "Método no permitido. Usa PATCH",
            data: {}
        });
    }

    const { userId, username, ip, port, avatar } = req.body;
    if (!userId) {
        return res.status(400).json({
            success: false,
            message: "Faltan datos obligatorios (userId)",
            data: {}
        });
    }

    try {
        const userRef = db.collection('users').doc(userId);
        let updateData = {};

        if (username) updateData.username = username;
        if (ip) updateData.ip = ip;
        if (port) updateData.port = port;

        let avatarUrl = null;
        if (avatar && avatar.imageData) {
            try {
                const avatarPath = `avatars/${userId}.jpg`;
                const avatarFile = bucket.file(avatarPath);

                await avatarFile.delete({ ignoreNotFound: true });

                const avatarBuffer = Buffer.from(avatar.imageData, 'base64');

                await avatarFile.save(avatarBuffer, {
                    metadata: { contentType: 'image/jpeg' },
                });

                await avatarFile.makePublic();
                avatarUrl = avatarFile.publicUrl();

                updateData.avatar = {
                    localPath: null,
                    storageUrl: avatarUrl,
                    imageData: null
                }
            } catch (error) {
                console.error(`Error al subir avatar: ${error}`);
                return res.status(500).json({
                    success: false,
                    message: "Error al subir avatar",
                    data: { error: error.message }
                });
            }
        }

        updateData.lastUpdated = admin.firestore.FieldValue.serverTimestamp();

        if (Object.keys(updateData).length === 1) {
            return res.status(400).json({
                success: false,
                message: "No se enviaron datos para actualizar.",
                data: {}
            });
        }

        await userRef.update(updateData);

        updateData.userId = userId;

        return res.status(200).json({
            success: true,
            message: `Usuario ${userId} actualizado correctamente.`,
            data: { user: updateData }
        });

    } catch (error) {
        console.error("Error al actualizar usuario:", error);
        return res.status(500).json({
            success: false,
            message: "Error al actualizar usuario",
            data: { error: error.message }
        });
    }
});

/**
 * Agrega un contacto a un usuario (relación bidireccional)
 */
exports.addContact = functions.https.onRequest(async (req, res) => {
    if (req.method !== 'PATCH') {
        return res.status(405).json({
            success: false,
            message: "Método no permitido. Usa PATCH",
            data: {}
        });
    }

    const { userId, contactId } = req.body;
    if (!userId || !contactId) {
        return res.status(400).json({
            success: false,
            message: "Faltan datos obligatorios (userId, contactId)",
            data: {}
        });
    }

    if (userId === contactId) {
        return res.status(400).json({
            success: false,
            message: "No puedes añadirte a ti mismo como contacto",
            data: {}
        });
    }

    try {
        const userRef = db.collection('users').doc(userId);
        const contactRef = db.collection('users').doc(contactId);
        
        const [userDoc, contactDoc] = await Promise.all([
            userRef.get(),
            contactRef.get()
        ]);

        if (!userDoc.exists) {
            return res.status(404).json({
                success: false,
                message: `Usuario ${userId} no encontrado`,
                data: {}
            });
        }

        if (!contactDoc.exists) {
            return res.status(404).json({
                success: false,
                message: `Contacto ${contactId} no encontrado`,
                data: {}
            });
        }

        await db.runTransaction(async (transaction) => {
            transaction.update(userRef, {
                contacts: admin.firestore.FieldValue.arrayUnion(contactId),
                lastUpdated: admin.firestore.FieldValue.serverTimestamp()
            });
            
            transaction.update(contactRef, {
                contacts: admin.firestore.FieldValue.arrayUnion(userId),
                lastUpdated: admin.firestore.FieldValue.serverTimestamp()
            });
        });

        const contactData = contactDoc.data();
        
        const formattedContact = {
            userId: contactId,
            username: contactData.username,
            ip: contactData.ip,
            port: contactData.port,
            avatar: contactData.avatar ? {
                localPath: null,
                storageUrl: contactData.avatar.storageUrl || null,
                imageData: null
            } : null
        };

        return res.status(200).json({
            success: true,
            message: `Contacto agregado correctamente`,
            data: { 
                userId, 
                contactId,
                contact: formattedContact
            }
        });

    } catch (error) {
        console.error("Error al agregar contacto:", error);
        return res.status(500).json({
            success: false,
            message: "Error al agregar contacto",
            data: { error: error.message }
        });
    }
});

/**
 * Obtiene los datos de un usuario
 */
exports.getUser = functions.https.onRequest(async (req, res) => {
    if (req.method !== 'GET') {
        return res.status(405).json({
            success: false,
            message: "Método no permitido. Usa GET",
            data: {}
        });
    }

    const { userId } = req.query;
    if (!userId) {
        return res.status(400).json({
            success: false,
            message: "Faltan datos obligatorios (userId)",
            data: {}
        });
    }

    try {
        const userRef = db.collection('users').doc(userId);
        const userDoc = await userRef.get();

        if (!userDoc.exists) {
            return res.status(404).json({
                success: false,
                message: "Usuario no encontrado",
                data: {}
            });
        }

        let userData = userDoc.data();
        userData.userId = userId;
        let avatarObject = null;

        if (userData.avatar) {
            avatarObject = {
                localPath: null,
                storageUrl: userData.avatar.storageUrl || null,
                imageData: null,
            }
        }

        userData.avatar = avatarObject;

        return res.status(200).json({
            success: true,
            message: "Usuario encontrado",
            data: { user: userData }
        });

    } catch (error) {
        console.error("Error al obtener usuario:", error);
        return res.status(500).json({
            success: false,
            message: "Error al obtener usuario",
            data: { error: error.message }
        });
    }
});

/**
 * Elimina un contacto de un usuario (relación bidireccional)
 */
exports.removeContact = functions.https.onRequest(async (req, res) => {
    if (req.method !== 'PATCH') {
        return res.status(405).json({
            success: false,
            message: "Método no permitido. Usa PATCH",
            data: {}
        });
    }

    const { userId, contactId } = req.body;

    if (!userId || !contactId) {
        return res.status(400).json({
            success: false,
            message: "Faltan datos obligatorios (userId, contactId)",
            data: {}
        });
    }

    try {
        const userRef = db.collection('users').doc(userId);
        const contactRef = db.collection('users').doc(contactId);
        
        const [userDoc, contactDoc] = await Promise.all([
            userRef.get(),
            contactRef.get()
        ]);

        if (!userDoc.exists) {
            return res.status(404).json({
                success: false,
                message: "Usuario no encontrado",
                data: {}
            });
        }

        if (!contactDoc.exists) {
            return res.status(404).json({
                success: false,
                message: "Contacto no encontrado",
                data: {}
            });
        }

        const userContacts = userDoc.data().contacts || [];
        if (!userContacts.includes(contactId)) {
            return res.status(400).json({
                success: false,
                message: `El usuario ${userId} no tiene agregado al contacto ${contactId}.`,
                data: { userId, contactId }
            });
        }

        await db.runTransaction(async (transaction) => {
            transaction.update(userRef, {
                contacts: admin.firestore.FieldValue.arrayRemove(contactId),
                lastUpdated: admin.firestore.FieldValue.serverTimestamp()
            });
            
            transaction.update(contactRef, {
                contacts: admin.firestore.FieldValue.arrayRemove(userId),
                lastUpdated: admin.firestore.FieldValue.serverTimestamp()
            });
        });

        const contactData = contactDoc.data();
        
        const contact = {
            userId: contactId,
            username: contactData.username,
            ip: contactData.ip,
            port: contactData.port,
            avatar: contactData.avatar ? {
                localPath: null,
                storageUrl: contactData.avatar.storageUrl || null,
                imageData: null
            } : null
        };

        return res.status(200).json({
            success: true,
            message: `Contacto eliminado correctamente (relación bidireccional)`,
            data: { 
                userId, 
                contactId,
                contact: contact
            }
        });

    } catch (error) {
        console.error("Error al eliminar contacto:", error);
        return res.status(500).json({
            success: false,
            message: "Error al eliminar contacto",
            data: { error: error.message }
        });
    }
});

/**
 * Obtiene la lista de contactos de un usuario
 */
exports.getContacts = functions.https.onRequest(async (req, res) => {
    if (req.method !== 'GET') {
        return res.status(405).json({
            success: false,
            message: "Método no permitido. Usa GET",
            data: {}
        });
    }

    const { userId } = req.query;

    if (!userId) {
        return res.status(400).json({
            success: false,
            message: "Faltan datos obligatorios (userId)",
            data: {}
        });
    }

    try {
        const userRef = db.collection('users').doc(userId);
        const userDoc = await userRef.get();

        if (!userDoc.exists) {
            return res.status(404).json({
                success: false,
                message: "Usuario no encontrado",
                data: {}
            });
        }

        const contacts = userDoc.data().contacts || [];

        if (contacts.length === 0) {
            return res.status(200).json({
                success: true,
                message: "El usuario no tiene contactos.",
                data: { contacts: [] }
            });
        }

        const contactRefs = contacts.map(contactId => db.collection('users').doc(contactId));
        const contactDocs = await db.getAll(...contactRefs);

        const contactsData = contactDocs
            .filter(doc => doc.exists)
            .map(doc => {
                const contactData = doc.data();
                return {
                    userId: doc.id,
                    username: contactData.username,
                    ip: contactData.ip,
                    port: contactData.port,
                    avatar: contactData.avatar ? {
                        localPath: null,
                        storageUrl: contactData.avatar.storageUrl || null,
                        imageData: null
                    } : null,
                    contacts: []
                };
            });

        const foundContactsIds = contactsData.map(contact => contact.userId);

        return res.status(200).json({
            success: true,
            message: "Lista de contactos obtenida correctamente",
            data: { 
                contacts: contactsData,
                totalContacts: contactsData.length,
            }
        });

    } catch (error) {
        console.error("Error al obtener contactos:", error);
        return res.status(500).json({
            success: false,
            message: "Error al obtener contactos",
            data: { error: error.message }
        });
    }
});
