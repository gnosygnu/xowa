local ParserFunctions = {}
local php

function ParserFunctions.expr( expression )
	return php.expr( expression )
end

function ParserFunctions.setupInterface( options )
	-- Boilerplate
	ParserFunctions.setupInterface = nil
	php = mw_interface
	mw_interface = nil

	-- Register this library in the "mw" global
	mw = mw or {}
	mw.ext = mw.ext or {}
	mw.ext.ParserFunctions = ParserFunctions

	package.loaded['mw.ext.ParserFunctions'] = ParserFunctions
end

return ParserFunctions
