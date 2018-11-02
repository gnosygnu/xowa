local TitleBlacklist = {}
local php

function TitleBlacklist.test( action, title )
	return php.test( action, title )
end

function TitleBlacklist.setupInterface( options )
	-- Boilerplate
	TitleBlacklist.setupInterface = nil
	php = mw_interface
	mw_interface = nil

	-- Register this library in the "mw" global
	mw = mw or {}
	mw.ext = mw.ext or {}
	mw.ext.TitleBlacklist = TitleBlacklist

	package.loaded['mw.ext.TitleBlacklist'] = TitleBlacklist
end

return TitleBlacklist
