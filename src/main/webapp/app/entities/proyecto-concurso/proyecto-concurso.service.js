(function() {
    'use strict';
    angular
        .module('cumminsApp')
        .factory('ProyectoConcurso', ProyectoConcurso);

    ProyectoConcurso.$inject = ['$resource'];

    function ProyectoConcurso ($resource) {
        var resourceUrl =  'api/proyecto-concursos/:id';

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
