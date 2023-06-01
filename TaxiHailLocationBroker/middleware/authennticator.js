const jwt = require('jsonwebtoken');

const authenticator = (req, res, next) => {
    // Get the token from the request header
    const authorizationHeader = req.header('Authorization');

    // Check if the token exists
    if (authorizationHeader && authorizationHeader.startsWith('Bearer ')) {
        // Extract the token from the authorization header
        const token = authorizationHeader.slice(7);
        try {
            // Verify and decode the JWT token
            const decoded = jwt.decode(token);
            req.user = decoded;

            next();
        } catch (error) {
            // Return an error response if the token is invalid or expired
            res.status(401).json({ error: 'Invalid or expired token', ex: error });
        }
    } else {
        // Return an error response if the bearer token is missing or malformed
        res.status(401).json({ error: 'Bearer token missing or malformed' });
    }
}

module.exports = authenticator;
