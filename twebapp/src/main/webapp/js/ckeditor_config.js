/**
 * @license Copyright (c) 2003-2013, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */
//CKEDITOR.plugins.addExternal('fmath_formula', 'plugins/fmath_formula/', 'plugin.js');
//CKEDITOR.plugins.addExternal('eqneditor', 'plugins/eqneditor/', 'plugin.js');
//CKEDITOR.plugins.addExternal('simple-image-browser', 'plugins/simple-image-browser/', 'plugin.js');
//CKEDITOR.plugins.addExternal('pastebase64', 'plugins/pastebase64/', 'plugin.js');
//CKEDITOR.config.simpleImageBrowserURL = 'http://localhost:8080/img/';
CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	//config.language = 'fr';
	//config.uiColor = '#AADC6E';
	config.removePlugins = 'forms';
	config.entities_greek = false;

//config.extraPlugins = 'eqneditor';

config.extraPlugins = 'fmath_formula,eqneditor';


/*
config.toolbar = [
			['Templates', 'Styles','Format','Font','FontSize','TextColor','BGColor','Maximize','Image'],
			['Source'],
			['Bold','Italic','Underline','Strike','-','Subscript','Superscript','-','fmath_formula'],
			['Table','HorizontalRule'],
			['NumberedList','BulletedList','-','Outdent','Indent','Blockquote']
		    ]
*/







};
