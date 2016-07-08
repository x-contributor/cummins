(function() {
    'use strict';
    angular
        .module('cumminsApp')
        .factory('VoluntarioVisitador', VoluntarioVisitador);

    VoluntarioVisitador.$inject = ['$resource'];

    function VoluntarioVisitador ($resource) {
        var resourceUrl =  'api/voluntario-visitadors/:id';

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
