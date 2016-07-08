(function() {
    'use strict';
    angular
        .module('cumminsApp')
        .factory('ParticipanteDescripcion', ParticipanteDescripcion);

    ParticipanteDescripcion.$inject = ['$resource'];

    function ParticipanteDescripcion ($resource) {
        var resourceUrl =  'api/participante-descripcions/:id';

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
