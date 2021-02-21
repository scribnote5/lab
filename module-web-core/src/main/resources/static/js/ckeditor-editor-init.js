/* ckeditor init */
let editor;

$(document).ready(function () {
    ClassicEditor
        .create(document.getElementById("content"), {
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
            editor = newEditor;
        })
        .catch(error => {
            console.error(error);
        });
});

/* ckeditor custom image upload */
class UploadAdapter {
    constructor(loader) {
        this.loader = loader;
    }

    upload() {
        return this.loader.file.then(file => new Promise(((resolve, reject) => {
            this._initRequest();
            this._initListeners(resolve, reject, file);
            this._sendRequest(file);
        })))
    }

    _initRequest() {
        const xhr = this.xhr = new XMLHttpRequest();
        xhr.open('POST', moduleAppApiAddress + '/api/attachedFiles/upload', true);
        xhr.responseType = 'json';
    }

    _initListeners(resolve, reject, file) {
        const xhr = this.xhr;
        const loader = this.loader;
        const genericErrorText = 'Couldn\'t upload file.'

        xhr.addEventListener('error', () => {
            reject(genericErrorText)
        })
        xhr.addEventListener('abort', () => reject())
        xhr.addEventListener('load', () => {
            const response = xhr.response
            if (!response || response.error) {
                return reject(response && response.error ? response.error.message : genericErrorText);
            }

            resolve({
                default: response.url //업로드된 파일 주소
            })
        })
    }

    _sendRequest(file) {
        const data = new FormData()
        data.append("files", file)
        this.xhr.send(data)
    }
}

function CustomUploadAdapterPlugin(editor) {
    editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
        return new UploadAdapter(loader)
    }
}
