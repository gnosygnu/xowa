--XOWA:updated 2019-08-19
--[[
	Registers and defines functions to handle Wikibase Entities through the Scribunto extension.

	@since 0.5

	@license GNU GPL v2+
	@author Marius Hoch < hoo@online.de >
	@author Bene* < benestar.wikimedia@gmail.com >
]]

local php = mw_interface
mw_interface = nil
local Entity = {}
local metatable = {}
local methodtable = {}
local util = require 'libraryUtil'
local checkType = util.checkType
local checkTypeMulti = util.checkTypeMulti

metatable.__index = methodtable

-- Claim ranks (Claim::RANK_* in PHP)
Entity.claimRanks = {
	RANK_TRUTH = 3,
	RANK_PREFERRED = 2,
	RANK_NORMAL = 1,
	RANK_DEPRECATED = 0
}

-- Is this a valid property id (Pnnn)?
--
-- @param {string} propertyId
local isValidPropertyId = function( propertyId )
	return type( propertyId ) == 'string' and propertyId:match( '^P[1-9]%d*$' )
end

-- Log access to claims of entity
--
-- @param {string} entityId
-- @param {string} propertyId
local addStatementUsage = function( entityId, propertyId )
	if isValidPropertyId( propertyId ) then
		-- Only attempt to track the usage if we have a valid property id.
		php.addStatementUsage( entityId, propertyId )
	end
end

-- Function to mask an entity's subtables in order to log access
-- Code for logging based on: http://www.lua.org/pil/13.4.4.html
--
-- @param {table} entity
-- @param {string} tableName
-- @param {function} usageFunc
local maskEntityTable = function( entity, tableName, usageFunc )
	if entity[tableName] == nil then
		return
	end
	local actualEntityTable = entity[tableName]
	entity[tableName] = {}

	local pseudoTableMetatable = {}
	pseudoTableMetatable.__index = function( _, key )
		usageFunc( entity.id, key )
		return actualEntityTable[key]
	end

	pseudoTableMetatable.__newindex = function( _, _, _ )
		error( 'Entity cannot be modified', 2 )
	end

	local logNext = function( _, key )
		local k, v = next( actualEntityTable, key )
		if k ~= nil then
			usageFunc( entity.id, k )
		end
		return k, v
	end

	pseudoTableMetatable.__pairs = function( _ )
		return logNext, {}, nil
	end

	setmetatable( entity[tableName], pseudoTableMetatable )
end

local noUsageTracking = function()
end

-- Function to mask an entity's subtables in order to log access and prevent modifications
--
-- @param {table} entity
-- @param {bool} fineGrainedTracking
local maskEntityTables = function ( entity, fineGrainedTracking )
	if fineGrainedTracking then
		maskEntityTable( entity, 'claims', addStatementUsage )
		maskEntityTable( entity, 'labels', php.addLabelUsage )
		maskEntityTable( entity, 'sitelinks', php.addSiteLinksUsage )
		maskEntityTable( entity, 'descriptions', php.addDescriptionUsage )
		maskEntityTable( entity, 'aliases', php.addOtherUsage )
	else
		maskEntityTable( entity, 'claims', noUsageTracking )
		maskEntityTable( entity, 'labels', noUsageTracking )
		maskEntityTable( entity, 'sitelinks', noUsageTracking )
		maskEntityTable( entity, 'descriptions', noUsageTracking )
		maskEntityTable( entity, 'aliases', noUsageTracking )
	end
end

-- Create new entity object from given data
--
-- @param {table} data
Entity.create = function( data )
	if type( data ) ~= 'table' then
		error( 'Expected a table obtained via mw.wikibase.getEntityObject, got ' .. type( data ) .. ' instead' )
	end
	if next( data ) == nil then
		error( 'Expected a non-empty table obtained via mw.wikibase.getEntityObject' )
	end
	if type( data.schemaVersion ) ~= 'number' then
		error( 'data.schemaVersion must be a number, got ' .. type( data.schemaVersion ) .. ' instead' )
	end
	if data.schemaVersion < 2 then
		error( 'mw.wikibase.entity must not be constructed using legacy data' )
	end
	if type( data.id ) ~= 'string' then
		error( 'data.id must be a string, got ' .. type( data.id ) .. ' instead' )
	end

	local entity = data
	maskEntityTables( entity, php.getSetting( 'fineGrainedLuaTracking' ) )

	setmetatable( entity, metatable )
	return entity
end

-- Get the id serialization from this entity.
methodtable.getId = function( entity )
	return entity.id
end

-- Get a term of a given type for a given language code or the content language (on monolingual wikis)
-- or the user's language (on multilingual wikis).
-- Second return parameter is the language the term is in.
--
-- @param {table} entity
-- @param {string} termType A valid key in the entity table (either labels, descriptions or aliases)
-- @param {string|number} langCode
local getTermAndLang = function( entity, termType, langCode )
	php.incrementStatsKey( 'wikibase.client.scribunto.entity.getTermAndLang.call' )

	langCode = langCode or php.getLanguageCode()

	if langCode == nil then
		return nil, nil
	end

	if entity[termType] == nil then
		return nil, nil
	end

	local term = entity[termType][langCode]

	if term == nil then
		return nil, nil
	end

	local actualLang = term.language or langCode
	return term.value, actualLang
end

-- Get the label for a given language code or the content language (on monolingual wikis)
-- or the user's language (on multilingual wikis).
--
-- @param {string|number} [langCode]
methodtable.getLabel = function( entity, langCode )
	checkTypeMulti( 'getLabel', 1, langCode, { 'string', 'number', 'nil' } )

	local label = getTermAndLang( entity, 'labels', langCode )
	return label
end

-- Get the description for a given language code or the content language (on monolingual wikis)
-- or the user's language (on multilingual wikis).
--
-- @param {string|number} [langCode]
methodtable.getDescription = function( entity, langCode )
	checkTypeMulti( 'getDescription', 1, langCode, { 'string', 'number', 'nil' } )

	local description = getTermAndLang( entity, 'descriptions', langCode )
	return description
end

-- Get the label for a given language code or the content language (on monolingual wikis)
-- or the user's language (on multilingual wikis).
-- Has the language the returned label is in as an additional second return parameter.
--
-- @param {string|number} [langCode]
methodtable.getLabelWithLang = function( entity, langCode )
	checkTypeMulti( 'getLabelWithLang', 1, langCode, { 'string', 'number', 'nil' } )

	return getTermAndLang( entity, 'labels', langCode )
end

-- Get the description for a given language code or the content language (on monolingual wikis)
-- or the user's language (on multilingual wikis).
-- Has the language the returned description is in as an additional second return parameter.
--
-- @param {string|number} [langCode]
methodtable.getDescriptionWithLang = function( entity, langCode )
	checkTypeMulti( 'getDescriptionWithLang', 1, langCode, { 'string', 'number', 'nil' } )

	return getTermAndLang( entity, 'descriptions', langCode )
end

-- Get the sitelink title linking to the given site id
--
-- @param {string|number} [globalSiteId]
methodtable.getSitelink = function( entity, globalSiteId )
	php.incrementStatsKey( 'wikibase.client.scribunto.entity.getSitelink.call' )

	checkTypeMulti( 'getSitelink', 1, globalSiteId, { 'string', 'number', 'nil' } )

	if entity.sitelinks == nil then
		return nil
	end

	globalSiteId = globalSiteId or php.getGlobalSiteId()

	if globalSiteId == nil then
		return nil
	end

	local sitelink = entity.sitelinks[globalSiteId]

	if sitelink == nil then
		return nil
	end

	return sitelink.title
end

-- @param {table} entity
-- @param {string} propertyLabelOrId
-- @param {string} funcName for error logging
local getEntityStatements = function( entity, propertyLabelOrId, funcName )
	php.incrementStatsKey( 'wikibase.client.scribunto.entity.getEntityStatements.call' )

	checkType( funcName, 1, propertyLabelOrId, 'string' )

	if not entity.claims then
		return {}
	end

	local propertyId = propertyLabelOrId
	if not isValidPropertyId( propertyId ) then
		propertyId = mw.wikibase.resolvePropertyId( propertyId )
	end

	if propertyId and entity.claims[propertyId] then
		return entity.claims[propertyId]
	end

	return {}
end

-- Get the best statements with the given property id or label
--
-- @param {string} propertyLabelOrId
methodtable.getBestStatements = function( entity, propertyLabelOrId )
	local entityStatements = getEntityStatements( entity, propertyLabelOrId, 'getBestStatements' )
	local statements = {}
	local bestRank = 'normal'

	for _, statement in pairs( entityStatements ) do
		if statement.rank == bestRank then
			statements[#statements + 1] = statement
		elseif statement.rank == 'preferred' then
			statements = { statement }
			bestRank = 'preferred'
		end
	end

	return statements
end

-- Get all statements with the given property id or label
--
-- @param {string} propertyLabelOrId
methodtable.getAllStatements = function( entity, propertyLabelOrId )
	return getEntityStatements( entity, propertyLabelOrId, 'getAllStatements' )
end

-- Get a table with all property ids attached to the entity.
methodtable.getProperties = function( entity )
	php.incrementStatsKey( 'wikibase.client.scribunto.entity.getProperties.call' )

	if entity.claims == nil then
		return {}
	end

	-- Get the keys (property ids)
	local properties = {}

	local n = 0
	for k, _ in pairs( entity.claims ) do
		n = n + 1
		properties[n] = k
	end

	return properties
end

-- Get the formatted value of the claims with the given property id
--
-- @param {table} entity
-- @param {string} phpFormatterFunction
-- @param {string} propertyLabelOrId
-- @param {table} [acceptableRanks]
local formatValuesByPropertyId = function( entity, phpFormatterFunction, propertyLabelOrId, acceptableRanks )
	acceptableRanks = acceptableRanks or nil

	local formatted = php[phpFormatterFunction](
		entity.id,
		propertyLabelOrId,
		acceptableRanks
	)

	local label
	if isValidPropertyId( propertyLabelOrId ) then
		label = mw.wikibase.getLabel( propertyLabelOrId )
	end

	if label == nil then
		-- Make the label fallback on the entity id for convenience/ consistency
		label = propertyLabelOrId
	end

	return {
		value = formatted,
		label = label
	}
end

-- Format the main Snaks belonging to a Statement (which is identified by a PropertyId
-- or the label of a Property) as wikitext escaped plain text.
--
-- @param {string} propertyLabelOrId
-- @param {table} [acceptableRanks]
methodtable.formatPropertyValues = function( entity, propertyLabelOrId, acceptableRanks )
	php.incrementStatsKey( 'wikibase.client.scribunto.entity.formatPropertyValues.call' )

	checkType( 'formatPropertyValues', 1, propertyLabelOrId, 'string' )
	checkTypeMulti( 'formatPropertyValues', 2, acceptableRanks, { 'table', 'nil' } )

	return formatValuesByPropertyId(
		entity,
		'formatPropertyValues',
		propertyLabelOrId,
		acceptableRanks
	);
end

-- Format the main Snaks belonging to a Statement (which is identified by a PropertyId
-- or the label of a Property) as rich wikitext.
--
-- @param {string} propertyLabelOrId
-- @param {table} [acceptableRanks]
methodtable.formatStatements = function( entity, propertyLabelOrId, acceptableRanks )
	php.incrementStatsKey( 'wikibase.client.scribunto.entity.formatStatements.call' )

	checkType( 'formatStatements', 1, propertyLabelOrId, 'string' )
	checkTypeMulti( 'formatStatements', 2, acceptableRanks, { 'table', 'nil' } )

	return formatValuesByPropertyId(
		entity,
		'formatStatements',
		propertyLabelOrId,
		acceptableRanks
	);
end

mw.wikibase.entity = Entity
package.loaded['mw.wikibase.entity'] = Entity

return Entity
