local FlaggedRevs = {}
local php

function FlaggedRevs.getStabilitySettings( pagename )
	if type( pagename ) == 'table' and pagename.prefixedText then
		pagename = pagename.prefixedText
	end
	return php.getStabilitySettings( pagename )
end

function FlaggedRevs.setupInterface( options )
	-- Boilerplate
	FlaggedRevs.setupInterface = nil
	php = mw_interface
	mw_interface = nil

	-- Register this library in the "mw" global
	mw = mw or {}
	mw.ext = mw.ext or {}
	mw.ext.FlaggedRevs = FlaggedRevs

	package.loaded['mw.ext.FlaggedRevs'] = FlaggedRevs
end

return FlaggedRevs
