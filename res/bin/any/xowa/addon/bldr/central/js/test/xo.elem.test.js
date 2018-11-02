/*jslint node: true */
"use strict";
var QUnit = window.QUnit;
var xo = window.xo;
QUnit.module("elem", {
    beforeEach: function (assert) {
        xo.mode_is_debug = false;
    }
    // afterEach: function( assert ) {}
});
QUnit.test("get.null", function (assert) {
    assert.notOk(xo.elem.get('missing'));
});
QUnit.test("make.body", function (assert) {
    var rslt = xo.elem.make(null, 'div', 'make_body_1');
    assert.equal(rslt.nodeName, 'DIV');
    assert.equal(rslt.id, 'make_body_1');
    assert.equal(rslt.parentElement, document.documentElement);
    assert.equal(rslt, document.getElementById('make_body_1'));
});
QUnit.test("make.owner", function (assert) {
    var make_owner_1 = xo.elem.make(null, 'div', 'make_owner_1'),
        make_owner_2 = xo.elem.make('make_owner_1', 'div', 'make_owner_2');
    assert.equal(make_owner_2.id, 'make_owner_2');
    assert.equal(make_owner_2.parentElement, make_owner_1);
    assert.equal(make_owner_2, document.getElementById('make_owner_2'));
});
QUnit.test("bind", function (assert) {
    var elem_1 = xo.elem.make(null, 'span', 'bind_1');
    xo.elem.bind_onclick(function () {return 'test'; }, 'bind_1');
    var actl = elem_1.onclick();
    assert.equal('test', actl);
});
