/* global array, tag id, index, load content */
function createEditor(array, id, index, content) {
    return ClassicEditor
        .create(document.getElementById(id), {
            toolbar: {
                items: [
                    'heading',
                    '|',
                    'fontFamily',
                    'fontSize',
                    'fontColor',
                    'fontBackgroundColor',
                    'highlight',
                    '|',
                    'blockQuote',
                    'bold',
                    'italic',
                    'underline',
                    'strikethrough',
                    'removeFormat',
                    '|',
                    'alignment',
                    '|',
                    'bulletedList',
                    'numberedList',
                    'indent',
                    'outdent',
                    '|',
                    'horizontalLine',
                    'link',
                    'specialCharacters',
                    'superscript',
                    'subscript',
                    '|',
                    'imageInsert',
                    'insertTable',
                    'mediaEmbed',
                    'codeBlock',
                    'htmlEmbed',
                    '|',
                    'exportWord',
                    'exportPdf',
                    'undo',
                    'redo'
                ]
            },
            language: 'en',
            image: {
                // Configure the available styles.
                styles: [
                    'alignLeft', 'alignCenter', 'alignRight'
                ],

                // Configure the available image resize options.
                resizeOptions: [
                    {
                        name: 'imageResize:original',
                        label: 'Original',
                        value: null
                    },
                    {
                        name: 'imageResize:75',
                        label: '75%',
                        value: '75'
                    },
                    {
                        name: 'imageResize:50',
                        label: '50%',
                        value: '50'
                    }
                ],

                // You need to configure the image toolbar, too, so it shows the new style
                // buttons as well as the resize buttons.
                toolbar: [
                    'imageStyle:alignLeft', 'imageStyle:alignCenter', 'imageStyle:alignRight',
                    '|',
                    'imageResize',
                    '|',
                    'imageTextAlternative'
                ]
            },
            table: {
                contentToolbar: [
                    'tableColumn',
                    'tableRow',
                    'mergeTableCells',
                    'tableCellProperties',
                    'tableProperties'
                ]
            },
            extraPlugins: [CustomUploadAdapterPlugin]
        })
        .then(newEditor => {
            array[index] = newEditor;
            newEditor.setData(content);
        })
        .catch(error => {
            console.error(error);
        });
}