"use strict";


import '@testing-library/jest-dom';

const {JSDOM} = require('jsdom');

const script = require('../src/assets/js/script');
const path = require("path");
const html = require('fs').readFileSync(path.resolve(__dirname, '../src/index.html')).toString();

let dom = null;

jest.useFakeTimers();

beforeEach(() => {
    dom = new JSDOM(html, {runScripts: "dangerously"});
    global.document = dom.window.document;
    global.window = dom.window;
    document.documentElement.innerHTML = html;
});


describe('Testing the test library', () => {

    // HTML tags hard coded on purpose, for sequencing

    it('should have a H1 with Stratego in it', () => {
        //script.init();

        // it should start at 0
        expect(dom.window.document.querySelector('h1').innerHTML).toMatch("Stratego");


    });


});