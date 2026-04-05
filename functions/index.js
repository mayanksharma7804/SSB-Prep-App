const {onRequest} = require("firebase-functions/v2/https");
const admin = require("firebase-admin");
const crypto = require("crypto");

admin.initializeApp();

/**
 * Razorpay Webhook Handler
 * This function verifies the signature from Razorpay and updates the user's payment status in Firestore.
 */
exports.handleRazorpayWebhook = onRequest(async (req, res) => {
    // 1. Get your webhook secret from Razorpay Dashboard
    // IMPORTANT: In production, use Firebase Environment Configurations (functions.config())
    const RAZORPAY_SECRET = "YOUR_WEBHOOK_SECRET_HERE";

    const signature = req.headers["x-razorpay-signature"];

    // 2. Verify the signature
    const hmac = crypto.createHmac("sha256", RAZORPAY_SECRET);
    hmac.update(JSON.stringify(req.body));
    const expectedSignature = hmac.digest("hex");

    if (signature !== expectedSignature) {
        console.error("Invalid signature received from Razorpay");
        return res.status(400).send("Invalid signature");
    }

    // 3. Process the event
    const event = req.body.event;
    console.log(`Processing Razorpay event: ${event}`);

    if (event === "payment.captured") {
        const payment = req.body.payload.payment.entity;
        const userId = payment.notes.userId;

        if (!userId || userId === "unknown") {
            console.error("Payment captured but userId is missing in notes");
            return res.status(400).send("UserId missing");
        }

        try {
            // 4. Update Firestore
            await admin.firestore().collection("users").doc(userId).set({
                isPaid: true,
                lastPaymentId: payment.id,
                paymentStatus: "captured",
                updatedAt: admin.firestore.FieldValue.serverTimestamp()
            }, { merge: true });

            console.log(`Successfully updated payment status for user: ${userId}`);
            return res.status(200).send("OK");
        } catch (error) {
            console.error("Firestore update failed:", error);
            return res.status(500).send("Internal Server Error");
        }
    }

    // Handle other events if needed, otherwise return OK to Razorpay
    return res.status(200).send("Event ignored");
});
