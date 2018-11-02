function xowa_mathjax_init() {
  if (!window.xowa.js.mathJax) {
    window.xowa.js.mathJax = {};
    xowa.js.mathJax.run = function() {
      var math = document.querySelectorAll('[id^="xowa_math_txt"]');
      if (!math.length) return;
      if (window.mathJax_Config == null) {  // hookup math; needed if math is not loaded by page; EX: Go to home/wiki/Help:Diagnostics and hover over "Help:Diagnostics/Math"
        window.mathJax_Config = function () {
          MathJax.Hub.Config({
            root: window.xowa_root_dir + 'bin/any/xowa/xtns/Math/modules/mathjax',
            config: ["TeX-AMS-texvc_HTML.js"],
            "v1.0-compatible": false,
            styles: { ".mtext": { "font-family": "sans-serif ! important", "font-size": "80%" } },
            displayAlign: "left",
            menuSettings: { zoom: "Click" },
            "HTML-CSS": { imageFont: null, availableFonts: ["TeX"] }
          });
          MathJax.OutputJax.fontDir = window.xowa_root_dir + 'bin/any/xowa/xtns/Math/modules/mathjax/fonts';
        }
        var config = 'mathJax_Config();',
            script1 = document.createElement( 'script' ),
            script2 = document.createElement( 'script' );
        script1.setAttribute( 'type', 'text/x-mathjax-config' );
        script1.text = config;
        document.getElementsByTagName('head')[0].appendChild( script1 );

        script2.setAttribute( 'src', window.xowa_root_dir + 'bin/any/xowa/xtns/Math/modules/mathjax/MathJax.js?config=default' );
        document.getElementsByTagName('head')[0].appendChild( script2 );
      }
      else {
        MathJax.Hub.Queue(["Typeset",MathJax.Hub]); // run math manually; needed if math is already loaded on page, and hover has math; EX:en.w:Euler's_identity and hover over "Euler's number"
      }  
    };
  }
}
xowa_mathjax_init();
document.addEventListener( "DOMContentLoaded", function(){
  window.xowa.js.mathJax.run();
});
