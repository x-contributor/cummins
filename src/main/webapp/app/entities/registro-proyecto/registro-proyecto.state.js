(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('registroProyecto', {
            parent: 'app',
            url: '/registro-proyecto',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/registro-proyecto/registro-proyecto.html',
                    controller: 'RegistroProyectoController',
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



