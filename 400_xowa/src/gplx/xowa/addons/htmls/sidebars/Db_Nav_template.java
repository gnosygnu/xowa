package gplx.xowa.addons.htmls.sidebars;

import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.libs.files.Io_url;
import gplx.types.basics.utls.StringUtl;
import gplx.langs.jsons.Json_nde;
import gplx.langs.mustaches.JsonMustacheNde;
import gplx.langs.mustaches.Mustache_bfr;
import gplx.langs.mustaches.Mustache_render_ctx;
import gplx.langs.mustaches.Mustache_tkn_itm;
import gplx.langs.mustaches.Mustache_tkn_parser;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.langs.msgs.Xol_msg_mgr;

public class Db_Nav_template {
	public Mustache_tkn_itm Navigation_root() {return navigation_root;} private Mustache_tkn_itm navigation_root;
	private Xol_msg_mgr msg_mgr;
	private Json_nde msgdata;
	private Xowe_wiki wiki;
	private static boolean once = true;
	private static Mustache_tkn_itm menu_root;

	public static void Build_Sidebar(Xowe_wiki wiki, BryWtr bfr, byte[] id, byte[] text, byte[] itms) {
		if (once) {
			once = false;
			Io_url template_root = wiki.Appe().Fsys_mgr().Bin_any_dir().GenSubDir_nest("xowa", "xtns", "Skin-Vector", "templates");
			Mustache_tkn_parser parser = new Mustache_tkn_parser(template_root);
			menu_root = parser.Parse("Menu");
		}
		Json_nde data = s_getMenuData(wiki, text, itms, MENU_TYPE_PORTAL);

    	// Bry_bfr tmp_bfr = Bry_bfr_.New();
		Mustache_render_ctx mctx = new Mustache_render_ctx().Init(new JsonMustacheNde(data));
		Mustache_bfr mbfr = Mustache_bfr.New_bfr(bfr);
		menu_root.Render(mbfr, mctx);
        // byte[] result = mbfr.To_bry_and_clear();
        // System.out.println(String_.new_u8(result));
	}
	public void Init(Xowe_wiki wiki) {
		Io_url template_root = wiki.Appe().Fsys_mgr().Bin_any_dir().GenSubDir_nest("xowa", "xtns", "Skin-Vector", "templates");
		Mustache_tkn_parser parser = new Mustache_tkn_parser(template_root);
		navigation_root = parser.Parse("Navigation");
		this.wiki = wiki;

		msg_mgr = wiki.Lang().Msg_mgr();
		build_msg();

		Test();
	}

	private String[] msgs = new String[] {
						"vector-opt-out-tooltip",
						"vector-opt-out",
						"navigation-heading",
						"vector-action-toggle-sidebar",
						"vector-jumptonavigation",
						"vector-jumptosearch",
						"vector-jumptocontent",
						"sitesubtitle",
						"sitetitle",
						"tagline"
					};

	//all thes messages should be preprocessed (per language) as $data["msg-{$message}"] = $this->msg( $message )->text();
	private void build_msg() {
		int msg_len = msgs.length;
		msgdata = Json_nde.NewByVal();
		for (int i = 0; i < msg_len; i++) {
			String msg = msgs[i];
			msgdata.AddKvStr("msg-" + msg, msg_mgr.Val_by_str_or_empty(msg));
		}
	}
    private void Test() {
		Json_nde jnde = Json_nde.NewByVal();
    	jnde.AddKvStr("class", "CLASS");
    	jnde.AddKvStr("text", "TEXT");
    	jnde.AddKvStr("href", "URL_str");
    	jnde.AddKvBool("exists", true);
    	jnde.AddKvBool("primary", true);
    	jnde.AddKvStr("link-class", StringUtl.Empty);
    	jnde.AddKvStr("context", "subject");

        Json_nde namespaces = Json_nde.NewByVal();
        namespaces.AddKvNde("subject", jnde);

		//Json_nde data_namespaces = new Json_nde(null, -1);
		msgdata.AddKvNde("data-namespace-tabs",
			getMenuData(
				BryUtl.NewA7("namespaces"),
				namespaces,
				MENU_TYPE_TABS
			)
		);
                
		BryWtr tmp_bfr = BryWtr.New();
		Mustache_render_ctx mctx = new Mustache_render_ctx().Init(new JsonMustacheNde(msgdata));
		Mustache_bfr mbfr = Mustache_bfr.New_bfr(tmp_bfr);
		navigation_root.Render(mbfr, mctx);
		byte[] result = mbfr.To_bry_and_clear();
		System.out.println(StringUtl.NewU8(result));
	}

    /* Vector/SkinVector.php */
	private static int MENU_TYPE_DROPDOWN = 0, MENU_TYPE_TABS = 1, MENU_TYPE_PORTAL = 2, MENU_TYPE_DEFAULT = 3;
	private static byte[][] extraClasses = new byte[][] {
		BryUtl.NewA7("vector-menu vector-menu-dropdown vectorMenu"),
		BryUtl.NewA7("vector-menu vector-menu-tabs vectorTabs"),
		BryUtl.NewA7("vector-menu vector-menu-portal portal"),
		BryUtl.NewA7("vector-menu")
	};
	private Json_nde getMenuData(byte[] label, Json_nde urls, int type) { return getMenuData(label, urls, type, false); }
	private Json_nde getMenuData(byte[] label_bry, Json_nde urls, int type, boolean setLabelToSelected) {
//private function getMenuData(
//		string $label,
//		array $urls = [],
//		int $type = self::MENU_TYPE_DEFAULT,
//		bool $setLabelToSelected = false
//	) : array {
//		$skin = $this->getSkin();
//		$extraClasses = [
//			self::MENU_TYPE_DROPDOWN => 'vector-menu vector-menu-dropdown vectorMenu',
//			self::MENU_TYPE_TABS => 'vector-menu vector-menu-tabs vectorTabs',
//			self::MENU_TYPE_PORTAL => 'vector-menu vector-menu-portal portal',
//			self::MENU_TYPE_DEFAULT => 'vector-menu',
//		];
//		// A list of classes to apply the list element and override the default behavior.
//		$listClasses = [
//			// `.menu` is on the portal for historic reasons.
//			// It should not be applied elsewhere per T253329.
//			self::MENU_TYPE_DROPDOWN => 'menu vector-menu-content-list',
//		];
//		$isPortal = $type === self::MENU_TYPE_PORTAL;

		boolean isPortal = type == MENU_TYPE_PORTAL;

//		// For some menu items, there is no language key corresponding with its menu key.
//		// These inconsitencies are captured in MENU_LABEL_KEYS
//		$msgObj = $skin->msg( self::MENU_LABEL_KEYS[ $label ] ?? $label );
//
//		$props = [
//			'id' => "p-$label",

		String label = StringUtl.NewU8(label_bry);
		String msg = label; // for now
		String linkertooltip = StringUtl.Empty;
		String listClasses = type == MENU_TYPE_DROPDOWN
			? "menu vector-menu-content-list"
			: "vector-menu-content-list";
		String plabel = "p-" + label;

		Json_nde props = Json_nde.NewByVal();
		props.AddKvStr("id", plabel);

		// 'label-id' => "p-{$label}-label",
		props.AddKvStr("label-id", plabel +"-label");

		// If no message exists fallback to plain text (T252727)
		// 'label' => $msgObj->exists() ? $msgObj->text() : $label,
		props.AddKvStr("label", msg);

		// 'list-classes' => $listClasses[$type] ?? 'vector-menu-content-list',
		props.AddKvStr("list-classes", listClasses);

		// 'html-items' => '',

		// 'is-dropdown' => $type === self::MENU_TYPE_DROPDOWN,
		props.AddKvBool("is-dropdown", type == MENU_TYPE_DROPDOWN);
		props.AddKvStr("html-tooltip", wiki.Msg_mgr().Val_html_accesskey_and_title(plabel));

		// 'html-tooltip' => Linker::tooltip( 'p-' . $label ),
		props.AddKvStr("html-tooltip", linkertooltip);

//		foreach ( $urls as $key => $item ) {
//			$props['html-items'] .= $this->getSkin()->makeListItem( $key, $item );
//			// Check the class of the item for a `selected` class and if so, propagate the items
//			// label to the main label.
//			if ( $setLabelToSelected ) {
//				if ( isset( $item['class'] ) && stripos( $item['class'], 'selected' ) !== false ) {
//					$props['label'] = $item['text'];
//				}
//			}
//		}

    	props.AddKvStr("html-items", "<li id=\"ca-nstab-main\" class=\"selected\"><a href=\"/wiki/Main_Page\" title=\"View the content page [c]\" accesskey=\"c\">Main Page</a></li>");

//		$afterPortal = '';
//		if ( $isPortal ) {
//			// The BaseTemplate::getAfterPortlet method ran the SkinAfterPortlet
//			// hook and if content is added appends it to the html-after-portal method.
//			// This replicates that historic behaviour.
//			// This code should eventually be upstreamed to SkinMustache in core.
//			// Currently in production this supports the Wikibase 'edit' link.
//			$content = $this->getAfterPortlet( $label );
//			if ( $content !== '' ) {
//				$afterPortal = Html::rawElement(
//					'div',
//					[ 'class' => [ 'after-portlet', 'after-portlet-' . $label ] ],
//					$content
//				);
//			}
//		}
//		$props['html-after-portal'] = $afterPortal;
//
//		// Mark the portal as empty if it has no content
//		$class = ( count( $urls ) == 0 && !$props['html-after-portal'] )
//			? 'vector-menu-empty emptyPortlet' : '';
//		$props['class'] = trim( "$class $extraClasses[$type]" );
//		return $props;

		props.AddKvStr("class", extraClasses[type]);
		return props;
	}

	private static Json_nde s_getMenuData(Xowe_wiki wiki, byte[] label, byte[] urls, int type) { return s_getMenuData(wiki, label, urls, type, false); }
	private static Json_nde s_getMenuData(Xowe_wiki wiki, byte[] label_bry, byte[] urls, int type, boolean setLabelToSelected) {
		boolean isPortal = type == MENU_TYPE_PORTAL;

		String label = StringUtl.NewU8(label_bry);
		String msg = label; // for now
		String linkertooltip = StringUtl.Empty;
		String listClasses = type == MENU_TYPE_DROPDOWN
			? "menu vector-menu-content-list"
			: "vector-menu-content-list";
		String plabel = "p-" + label;

		Json_nde props = Json_nde.NewByVal();
		props.AddKvStr("id", plabel);
		props.AddKvStr("label-id", plabel +"-label");
		props.AddKvStr("label", msg);
		props.AddKvStr("list-classes", listClasses);
		props.AddKvBool("is-dropdown", type == MENU_TYPE_DROPDOWN);
		props.AddKvStr("html-tooltip", wiki.Msg_mgr().Val_html_accesskey_and_title(plabel));
		props.AddKvStr("html-tooltip", linkertooltip);

//		foreach ( $urls as $key => $item ) {
//			$props['html-items'] .= $this->getSkin()->makeListItem( $key, $item );
//			// Check the class of the item for a `selected` class and if so, propagate the items
//			// label to the main label.
//			if ( $setLabelToSelected ) {
//				if ( isset( $item['class'] ) && stripos( $item['class'], 'selected' ) !== false ) {
//					$props['label'] = $item['text'];
//				}
//			}
//		}
		props.AddKvStr("html-items", urls);

//		$afterPortal = '';
//		if ( $isPortal ) {
//			// The BaseTemplate::getAfterPortlet method ran the SkinAfterPortlet
//			// hook and if content is added appends it to the html-after-portal method.
//			// This replicates that historic behaviour.
//			// This code should eventually be upstreamed to SkinMustache in core.
//			// Currently in production this supports the Wikibase 'edit' link.
//			$content = $this->getAfterPortlet( $label );
//			if ( $content !== '' ) {
//				$afterPortal = Html::rawElement(
//					'div',
//					[ 'class' => [ 'after-portlet', 'after-portlet-' . $label ] ],
//					$content
//				);
//			}
//		}
//		$props['html-after-portal'] = $afterPortal;
//
//		// Mark the portal as empty if it has no content
//		$class = ( count( $urls ) == 0 && !$props['html-after-portal'] )
//			? 'vector-menu-empty emptyPortlet' : '';
//		$props['class'] = trim( "$class $extraClasses[$type]" );
//		return $props;
		props.AddKvStr("class", extraClasses[type]);
		return props;
	}
}