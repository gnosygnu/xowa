require('MWServer')
require('mwInit')
_G.getfenv = require 'compat_env'.getfenv
_G.setfenv = require 'compat_env'.setfenv
_G.loadstring = load
_G.unpack = table.unpack

server = MWServer:new()
return server
