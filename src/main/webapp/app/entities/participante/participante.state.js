(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('participante', {
            parent: 'entity',
            url: '/participante',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cumminsApp.participante.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/participante/participantes.html',
                    controller: 'ParticipanteController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('participante');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('participante-detail', {
            parent: 'entity',
            url: '/participante/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cumminsApp.participante.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/participante/participante-detail.html',
                    controller: 'ParticipanteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('participante');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Participante', function($stateParams, Participante) {
                    return Participante.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('participante.new', {
            parent: 'participante',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/participante/participante-dialog.html',
                    controller: 'ParticipanteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombreAc: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('participante', null, { reload: true });
                }, function() {
                    $state.go('participante');
                });
            }]
        })
        .state('participante.edit', {
            parent: 'participante',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/participante/participante-dialog.html',
                    controller: 'ParticipanteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Participante', function(Participante) {
                            return Participante.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('participante', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('participante.delete', {
            parent: 'participante',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/participante/participante-delete-dialog.html',
                    controller: 'ParticipanteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Participante', function(Participante) {
                            return Participante.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('participante', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
