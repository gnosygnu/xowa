// file:///C:/xowa/bin/any/xowa/html/res/src/xowa/timeline/timeline_test.html?random=false
describe("Timeline", function() {
  this.timeline = window.xowa.timeline;

  // beforeEach(function() {});
  describe("removeCommentsAndEmptyLines", function() {
    // utility test function
    test = function(raw, expd) {
      expect(self.timeline.removeCommentsAndEmptyLines(raw)).toEqual(expd);
    };

    // actual tests
    it("block comment: EX: '<# ... #>'", function() {
      test('a#>b<#c', 'ac');
    });
    
    it("eol comment: EX: '# ...\\n'", function() {
      test('a#b\nc', 'a\nc');
    });
    
    it("eol comment: noop if anchor: EX: '\"http://xowa.org/Main_Page#abc\"\\n'", function() {
      test('\"a#b\"\n', '\"a#b\"\n');
    });

    it("empty lines: EX: '\\n\\n'", function() {
      test('a\n\nb', 'a\nb');
    });
    
  });

});
