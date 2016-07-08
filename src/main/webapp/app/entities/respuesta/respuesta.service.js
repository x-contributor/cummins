(function() {
    'use strict';
    angular
        .module('cumminsApp')
        .factory('Respuesta', Respuesta);

    Respuesta.$inject = ['$resource'];

    function Respuesta ($resource) {
        var resourceUrl =  'api/respuestas/:id';

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
