(function() {
    'use strict';
    angular
        .module('cumminsApp')
        .factory('PreguntaSeccion', PreguntaSeccion);

    PreguntaSeccion.$inject = ['$resource'];

    function PreguntaSeccion ($resource) {
        var resourceUrl =  'api/pregunta-seccions/:id';

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
