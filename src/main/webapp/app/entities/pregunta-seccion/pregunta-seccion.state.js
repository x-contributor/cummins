(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pregunta-seccion', {
            parent: 'entity',
            url: '/pregunta-seccion',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cumminsApp.preguntaSeccion.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pregunta-seccion/pregunta-seccions.html',
                    controller: 'PreguntaSeccionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('preguntaSeccion');
                    $translatePartialLoader.addPart('tipoProyecto');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('pregunta-seccion-detail', {
            parent: 'entity',
            url: '/pregunta-seccion/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cumminsApp.preguntaSeccion.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pregunta-seccion/pregunta-seccion-detail.html',
                    controller: 'PreguntaSeccionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('preguntaSeccion');
                    $translatePartialLoader.addPart('tipoProyecto');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PreguntaSeccion', function($stateParams, PreguntaSeccion) {
                    return PreguntaSeccion.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('pregunta-seccion.new', {
            parent: 'pregunta-seccion',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pregunta-seccion/pregunta-seccion-dialog.html',
                    controller: 'PreguntaSeccionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                preguntaTitulo: null,
                                preguntaInstrucciones: null,
                                preguntaAyuda: null,
                                consecutivoSeccion: null,
                                tipoProyecto: null,
                                minimoRespuesta: null,
                                maximoRespuesta: null,
                                sumaTotal: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('pregunta-seccion', null, { reload: true });
                }, function() {
                    $state.go('pregunta-seccion');
                });
            }]
        })
        .state('pregunta-seccion.edit', {
            parent: 'pregunta-seccion',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pregunta-seccion/pregunta-seccion-dialog.html',
                    controller: 'PreguntaSeccionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PreguntaSeccion', function(PreguntaSeccion) {
                            return PreguntaSeccion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pregunta-seccion', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pregunta-seccion.delete', {
            parent: 'pregunta-seccion',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pregunta-seccion/pregunta-seccion-delete-dialog.html',
                    controller: 'PreguntaSeccionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PreguntaSeccion', function(PreguntaSeccion) {
                            return PreguntaSeccion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pregunta-seccion', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
