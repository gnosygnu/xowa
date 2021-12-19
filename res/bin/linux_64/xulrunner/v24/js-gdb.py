""" GDB Python customization auto-loader for js shell """

import os.path
sys.path[0:0] = [os.path.join('/builds/slave/rel-m-beta-xr_l64_bld-00000000/build/js/src', 'gdb')]

import mozilla.autoload
mozilla.autoload.register(gdb.current_objfile())
