const express = require('express');
const authenticator = require("../middleware/authennticator");
const {updateLocation} = require("../service/updateLocation");
const router = express.Router();

router.post("/updateLocation", authenticator,
    (req, res) => {

        const {driver, vehicle, vehicleType, location, inRide, ride, status, createdAt} = req.body;
        const [locationLatitude, locationLongitude] = location.split(',');

        updateLocation(driver, vehicle, vehicleType, parseFloat(locationLatitude), parseFloat(locationLongitude), inRide, ride, status, createdAt)
            .then(() => {
                res.status(200).json({message: 'Location updated successfully.'});
            })
            .catch((error) => {
                res.status(500).json({error: 'An error occurred while updating the location.'});
            });
    })

module.exports = router;