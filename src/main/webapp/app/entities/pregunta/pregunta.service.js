(function() {
    'use strict';
    angular
        .module('cumminsApp')
        .factory('Pregunta', Pregunta);

    Pregunta.$inject = ['$resource'];

    function Pregunta ($resource) {
        var resourceUrl =  'api/preguntas/:id';

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
