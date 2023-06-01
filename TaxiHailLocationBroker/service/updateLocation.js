const {produceMessage} = require("../util/kafkaproducer");
const updateLocation = (driver, vehicle, locationLatitude, locationLongitude, inRide, ride, status, createdAt) => {

    return new Promise((resolve, reject) => {

        const message = {
            driver,
            vehicle,
            locationLatitude,
            locationLongitude,
            inRide,
            ride,
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

module.exports = { updateLocation };
