local object = {}
local php

function object.setupInterface( options )
    -- Remove setup function
    object.setupInterface = nil

    -- Copy the PHP callbacks to a local variable, and remove the global
    php = mw_interface
    mw_interface = nil

    -- Install into the mw global
    mw = mw or {}
    mw.ext = mw.ext or {}
    mw.ext.data = object

    -- Indicate that we're loaded
    package.loaded['mw.ext.data'] = object
end

function object.get( title, language )
    return php.get( title, language )
end

return object
