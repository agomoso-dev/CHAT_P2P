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
        res.status(405).send('Método no permitido. Usa POST');
        return;
    }

    const { userId, username, ip, port, avatar } = req.body;
    if (!userId || !username || !ip || !port) {
        res.status(400).send('Faltan datos obligatorios');
        return;
    }

    try {
        let avatarUrl = null;

        if (avatar) {
            try {
                const avatarFile = bucket.file(`avatars/${userId}.jpg`);
                const avatarBuffer = Buffer.from(avatar, 'base64');

                await avatarFile.save(avatarBuffer, {
                    metadata: {
                        contentType: 'image/jpeg',
                    },
                });

                await avatarFile.makePublic();
                avatarUrl = avatarFile.publicUrl();
            } catch (error) {
                console.error(`Error al subir avatar: ${error}`);
                res.status(500).send('Error al subir avatar');
                return;
            }
        }

        await db.collection('users').doc(userId).set({
            username: username,
            ip: ip,
            port: port,
            avatar: avatarUrl,
            lastUpdated: admin.firestore.FieldValue.serverTimestamp()
        });

        res.status(200).json({
            message: 'Usuario agregado exitosamente',
            userId,
            avatarUrl
        });

    } catch (error) {
        console.error('Error guardando usuario:', error);
        res.status(500).send('Error al guardar el usuario en la base de datos');
    }
});

/**
 * Actualiza un usuario ya existente
 */
exports.updateUser = functions.https.onRequest(async (req, res) => {
    if (req.method !== 'PATCH') {
        res.status(405).json({ error: "Método no permitido. Usa PATCH" });
        return;
    }

    const { userId, username, ip, port, avatar } = req.body;
    if (!userId) {
        res.status(400).json({ error: "Faltan datos obligatorios (userId)" });
        return;
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
                    metadata: {
                        contentType: 'image/jpeg',
                    },
                });

                await avatarFile.makePublic();
                avatarUrl = avatarFile.publicUrl();

                updateData.avatar = avatarUrl;
            } catch (error) {
                console.error(`Error al subir avatar: ${error}`);
                res.status(500).json({ error: "Error al subir avatar", details: error.message });
                return;
            }
        }

        updateData.lastUpdated = admin.firestore.FieldValue.serverTimestamp();

        if (Object.keys(updateData).length === 1) {
            res.status(400).json({ error: "No se enviaron datos para actualizar." });
            return;
        }

        await userRef.update(updateData);

        res.status(200).json({
            success: true,
            message: `Usuario ${userId} actualizado correctamente.`,
            updatedFields: updateData
        });

    } catch (error) {
        res.status(500).json({ error: "Error al actualizar usuario", details: error.message });
    }
});


/**
 * Agrega un contacto a un usuario
 */
exports.addContact = functions.https.onRequest(async (req, res) => {
    if (req.method !== 'PATCH') {
        res.status(405).json({ error: "Método no permitido. Usa PATCH" });
        return;
    }

    const { userId, contactId } = req.body;
    if (!userId || !contactId) {
        res.status(400).json({ error: "Faltan datos obligatorios (userId, contactId)" });
        return;
    }

    try {
        const userRef = db.collection('users').doc(userId);
        await userRef.update({
            contacts: admin.firestore.FieldValue.arrayUnion(contactId)
        });

        res.status(200).json({
            success: true,
            message: `Contacto ${contactId} agregado a ${userId} correctamente.`
        });

    } catch (error) {
        res.status(500).json({ error: "Error al agregar contacto", details: error.message });
    }
});

/**
 * Obtiene los datos de un usuario
 */
exports.getUser = functions.https.onRequest(async (req, res) => {
    if (req.method !== 'GET') {
        res.status(405).json({ error: "Método no permitido. Usa GET" });
        return;
    }

    const { userId } = req.query;
    if (!userId) {
        res.status(400).json({ error: "Faltan datos obligatorios (userId)" });
        return;
    }

    try {
        const userRef = db.collection('users').doc(userId);
        const userDoc = userRef.get();

        if (!userDoc.exists) {
            res.status(404).json({ error: "Usuario no encontrado" });
            return;
        }

        res.status(200).json({ success: true, user: userDoc.data() });

    } catch (error) {
        res.status(500).json({ error: "Error al obtener usuario", details: error.message });
    }
});

/**
 * Elimina un contacto de un usuario
 */
exports.removeContact = functions.https.onRequest(async (req, res) => {
    if (req.method !== 'PATCH') {
        res.status(405).json({ error: "Método no permitido. Usa PATCH" });
        return;
    }

    const { userId, contactId } = req.body;
    if (!userId || !contactId) {
        res.status(400).json({ error: "Faltan datos obligatorios (userId, contactId)" });
        return;
    }

    try {
        const userRef = db.collection('users').doc(userId);
        await userRef.update({
            contacts: admin.firestore.FieldValue.arrayRemove(contactId)
        });

        res.status(200).json({
            success: true,
            message: `Contacto ${contactId} eliminado de ${userId} correctamente.`
        });

    } catch (error) {
        res.status(500).json({ error: "Error al eliminar contacto", details: error.message });
    }
});

/**
 * Obtiene la lista de contactos de un usuario
 */
exports.getContacts = functions.https.onRequest(async (req, res) => {
    if (req.method !== 'GET') {
        res.status(405).json({ error: "Método no permitido. Usa GET" });
        return;
    }

    const { userId } = req.query;
    if (!userId) {
        res.status(400).json({ error: "Faltan datos obligatorios (userId)" });
        return;
    }

    try {
        const userRef = db.collection('users').doc(userId);
        const userDoc = await userRef.get();

        if (!userDoc.exists) {
            res.status(404).json({ error: "Usuario no encontrado" });
            return;
        }

        const contacts = userDoc.data().contacts || [];

        if (contacts.length === 0) {
            res.status(200).json({ success: true, contacts: [] });
            return;
        }

        const contactRefs = contacts.map(contactId => db.collection('users').doc(contactId));
        const contactDocs = await db.getAll(...contactRefs);

        const contactsData = contactDocs.map(doc => 
            doc.exists ? { userId: doc.id, ...doc.data() } : { error: "Contacto no encontrado" }
        );

        res.status(200).json({ success: true, contacts: contactsData });

    } catch (error) {
        res.status(500).json({ error: "Error al obtener contactos", details: error.message });
    }
});
