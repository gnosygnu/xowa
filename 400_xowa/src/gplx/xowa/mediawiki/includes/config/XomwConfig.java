package gplx.xowa.mediawiki.includes.config;

// MW.SRC:1.33.1
/**
 * Interface for configuration instances
 *
 * @since 1.23
 */
public interface XomwConfig {

    /**
     * Get a configuration variable such as "Sitename" or "UploadMaintenance."
     *
     * @param string $name Name of configuration option
     * @return mixed Value configured
     * @throws ConfigException
     */
    public Object get(String name);

    /**
     * Check whether a configuration option is set for the given name
     *
     * @param string $name Name of configuration option
     * @return bool
     * @since 1.24
     */
    public boolean has(String name);
}
