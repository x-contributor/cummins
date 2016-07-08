(function() {
    'use strict';
    angular
        .module('cumminsApp')
        .factory('Contacto', Contacto);

    Contacto.$inject = ['$resource'];

    function Contacto ($resource) {
        var resourceUrl =  'api/contactos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
