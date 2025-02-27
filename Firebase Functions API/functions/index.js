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

        if (avatar) {
            const avatarFile = bucket.file(`avatars/${userId}.jpg`);
            const avatarBuffer = Buffer.from(avatar, 'base64');

            await avatarFile.save(avatarBuffer, {
                metadata: { contentType: 'image/jpeg' },
            });

            await avatarFile.makePublic();
            avatarUrl = avatarFile.publicUrl();
        }

        await db.collection('users').doc(userId).set({
            username,
            ip,
            port,
            avatar: avatarUrl,
            lastUpdated: admin.firestore.FieldValue.serverTimestamp()
        });

        const responseData = { userId };
        if (avatarUrl) responseData.avatarUrl = avatarUrl;

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
        if (avatar) {
            try {
                const avatarPath = `avatars/${userId}.jpg`;
                const avatarFile = bucket.file(avatarPath);

                await avatarFile.delete({ ignoreNotFound: true });

                const avatarBuffer = Buffer.from(avatar, 'base64');

                await avatarFile.save(avatarBuffer, {
                    metadata: { contentType: 'image/jpeg' },
                });

                await avatarFile.makePublic();
                avatarUrl = avatarFile.publicUrl();

                updateData.avatar = avatarUrl;
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

        return res.status(200).json({
            success: true,
            message: `Usuario ${userId} actualizado correctamente.`,
            data: updateData
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
 * Agrega un contacto a un usuario
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

    try {
        const userRef = db.collection('users').doc(userId);
        await userRef.update({
            contacts: admin.firestore.FieldValue.arrayUnion(contactId)
        });

        return res.status(200).json({
            success: true,
            message: `Contacto ${contactId} agregado a ${userId} correctamente.`,
            data: { userId, contactId }
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

        return res.status(200).json({
            success: true,
            message: "Usuario encontrado",
            data: userDoc.data()
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
 * Elimina un contacto de un usuario
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
        const userDoc = await userRef.get();

        if (!userDoc.exists) {
            return res.status(404).json({
                success: false,
                message: "Usuario no encontrado",
                data: {}
            });
        }

        const contacts = userDoc.data().contacts || [];
        
        if (!contacts.includes(contactId)) {
            return res.status(400).json({
                success: false,
                message: `El usuario ${userId} no tiene agregado al contacto ${contactId}.`,
                data: { userId, contactId }
            });
        }

        await userRef.update({
            contacts: admin.firestore.FieldValue.arrayRemove(contactId)
        });

        return res.status(200).json({
            success: true,
            message: `Contacto ${contactId} eliminado de ${userId} correctamente.`,
            data: { userId, contactId }
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
            .map(doc => doc.exists ? { userId: doc.id, ...doc.data() } : { error: "Contacto no encontrado" });

        return res.status(200).json({
            success: true,
            message: "Lista de contactos obtenida correctamente",
            data: { contacts: contactsData }
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
