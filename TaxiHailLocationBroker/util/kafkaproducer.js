const { Kafka } = require('kafkajs');

// Set up Kafka producer configuration
const kafka = new Kafka({
    clientId: 'location-broker',
    brokers: ['localhost:9092'],
});

// Create a producer instance
const producer = kafka.producer();

// Connect the producer to the Kafka broker
const connectProducer = async () => {
    try {
        await producer.connect();
        console.log('Kafka producer connected.');
    } catch (error) {
        console.error('Error connecting Kafka producer:', error);
    }
};

// Disconnect the producer from the Kafka broker
const disconnectProducer = async () => {
    try {
        await producer.disconnect();
        console.log('Kafka producer disconnected.');
    } catch (error) {
        console.error('Error disconnecting Kafka producer:', error);
    }
};

// Function to produce a message to a Kafka topic
const produceMessage = async (topic, message) => {
    try {
        // Produce the message
        await producer.send({
            topic,
            messages: [
                {
                    value: JSON.stringify(message), // Convert the message to a string
                },
            ],
        });
    } catch (error) {
        console.error('Error producing message:', error);
    }
};

module.exports = {
    connectProducer,
    disconnectProducer,
    produceMessage,
};
