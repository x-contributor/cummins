(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('registroPropuesta', {
            parent: 'app',
            url: '/registro-propuesta',
            data: {
                authorities: ['ROLE_USER']
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/registro-propuesta/registro-propuesta.html',
                    controller: 'RegistroPropuestaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('proyecto');
                    $translatePartialLoader.addPart('global');
                    $translatePartialLoader.addPart('participanteDescripcion');
                    $translatePartialLoader.addPart('contacto');
                    return $translate.refresh();
                }]
            }
        });
    }
})();



