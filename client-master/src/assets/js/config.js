"use strict";

const _REMOTE_API = 'https://project-i.ti.howest.be/stratego-23/api';
const _LOCAL_API = 'http://localhost:8080/';

const config = {
    localApi: true,
    getAPIUrl: function() { return this.localApi ? _LOCAL_API : _REMOTE_API; },
    defaultBoardSize: {rows: 10, cols: 10},
    clientValidation: true,
    shutUpSonarLint: true
};
