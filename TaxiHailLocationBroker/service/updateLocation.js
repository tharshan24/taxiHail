const {produceMessage} = require("../util/kafkaproducer");
const updateLocation = (driver, vehicle, vehicleType, locationLatitude, locationLongitude, inRide, ride, status, createdAt) => {

    return new Promise((resolve, reject) => {

        const defaultUUID = "00000000-0000-0000-0000-000000000000";

        const message = {
            driver,
            vehicle: vehicle || defaultUUID,
            vehicleType: vehicleType || defaultUUID,
            locationLatitude,
            locationLongitude,
            inRide,
            ride: ride || defaultUUID,
            status,
            createdAt
        };

        // Produce Kafka message
        produceMessage('location', message)
            .then(() => {
                resolve();
            })
            .catch((error) => {
                reject(error);
            });
    });

};

module.exports = {updateLocation};
