const initTinyMCE = id => {
    tinymce.init({
        selector: id || "[textarea-mce]",
        plugins: 'charmap codesample lists advlist media emoticons image link preview searchreplace table wordcount help',
        toolbar: 'undo redo | styles | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image | forecolor backcolor emoticons charmap | preview help',
        images_upload_url: `/${pathAdmin}/upload/image`
    });
}

initTinyMCE();