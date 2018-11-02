--XOWA:updated 2017-03-16
--[[
	Registers and defines functions to handle Wikibase Entities through the Scribunto extension.

	@since 0.5

	@license GNU GPL v2+
	@author Marius Hoch < hoo@online.de >
	@author Bene* < benestar.wikimedia@gmail.com >
]]

local php = mw_interface
local entity = {}
local metatable = {}
local methodtable = {}
local util = require 'libraryUtil'
local checkType = util.checkType
local checkTypeMulti = util.checkTypeMulti

metatable.__index = methodtable

-- Claim ranks (Claim::RANK_* in PHP)
entity.claimRanks = {
	RANK_TRUTH = 3,
	RANK_PREFERRED = 2,
	RANK_NORMAL = 1,
	RANK_DEPRECATED = 0
}

-- Create new entity object from given data
--
-- @param {table} data
entity.create = function( data )
	if type( data ) ~= 'table' or type( data.schemaVersion ) ~= 'number' then
		error( 'The entity data must be a table obtained via mw.wikibase.getEntityObject' )
	end

	if data.schemaVersion < 2 then
		error( 'mw.wikibase.entity must not be constructed using legacy data' )
	end

	local entity = data
	setmetatable( entity, metatable )

	return entity
end

-- Get a term of a given type for a given language code or the content language (on monolingual wikis)
-- or the user's language (on multilingual wikis).
-- Second return parameter is the language the term is in.
--
-- @param {table} entity
-- @param {string} termType A valid key in the entity table (either labels, descriptions or aliases)
-- @param {string|number} langCode
local getTermAndLang = function( entity, termType, langCode )
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

-- Get the best statements with the given property id
--
-- @param {string} propertyId
methodtable.getBestStatements = function( entity, propertyId )
	if entity.claims == nil or not entity.claims[propertyId] then
		return {}
	end

	local statements = {}
	local bestRank = 'normal'

	for k, statement in pairs( entity.claims[propertyId] ) do
		if statement.rank == bestRank then
			statements[#statements + 1] = statement
		elseif statement.rank == 'preferred' then
			statements = { statement }
			bestRank = 'preferred'
		end
	end

	return statements
end

-- Get a table with all property ids attached to the entity.
methodtable.getProperties = function( entity )
	if entity.claims == nil then
		return {}
	end

	-- Get the keys (property ids)
	local properties = {}

	local n = 0
	for k, v in pairs( entity.claims ) do
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
	if propertyLabelOrId:match( '^P%d+$' ) then
		label = mw.wikibase.label( propertyLabelOrId )
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
	checkType( 'formatStatements', 1, propertyLabelOrId, 'string' )
	checkTypeMulti( 'formatStatements', 2, acceptableRanks, { 'table', 'nil' } )

	return formatValuesByPropertyId(
		entity,
		'formatStatements',
		propertyLabelOrId,
		acceptableRanks
	);
end

mw.wikibase.entity = entity
package.loaded['mw.wikibase.entity'] = entity
mw_interface = nil

return entity
