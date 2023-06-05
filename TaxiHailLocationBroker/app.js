const express = require('express');
const path = require('path');
const cookieParser = require('cookie-parser');
const logger = require('morgan');
const {connectProducer} = require('./util/kafkaproducer');
const cors = require('cors');


const indexRouter = require('./routes/index');
const locationRouter = require('./routes/location');

const app = express();
app.use(cors());

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({extended: false}));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', indexRouter);
app.use('/location', locationRouter);

// Connect the Kafka producer on application startup
connectProducer()
    .then(() => {
        // Start your server or perform other initialization tasks
    })
    .catch((error) => {
        console.error('Error connecting Kafka producer:', error);
        process.exit(1); // Terminate the application if the Kafka producer fails to connect
    });


module.exports = app;
