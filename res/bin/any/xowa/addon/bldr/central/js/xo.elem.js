(function (xo) {
    "use strict";
    xo.mode_is_debug = false;
    xo.elem = new function () {
        this.get = function (elem_id) {
            var rv = document.getElementById(elem_id);
            if (!rv && xo.mode_is_debug) {
                alert('elem is null: ' + elem_id);
            }
            return rv;
        };

        this.make = function (owner_id, elem_type, elem_id) {
            var rv = document.createElement(elem_type);
            if (elem_id) {rv.id = elem_id; }
            var owner_elem = owner_id ? this.get(owner_id) : document.documentElement;
            if (!owner_elem && xo.mode_is_debug) {
                alert('owner elem is null: ' + owner_id);
            }
            owner_elem.appendChild(rv);
            return rv;
        };
        
        this.del = function (elem_id) {
            xo.log.add(1, 'elem.del.bgn', 'elem_id', elem_id);
            var elem = document.getElementById(elem_id);
            elem.parentNode.removeChild(elem);
            return true;
        };

        this.bind_onclick = function (func_obj, elem_id) {
            this.bind(func_obj, 'onclick', elem_id);
        }
        this.bind = function (func_obj, func_name, elem_id) {
            var elem = this.get(elem_id);
            elem[func_name] = func_obj;
        };
    };
}(window.xo = window.xo || {}));
