(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .controller('ParticipanteDetailController', ParticipanteDetailController);

    ParticipanteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Participante', 'Proyecto', 'Contacto', 'ParticipanteDescripcion'];

    function ParticipanteDetailController($scope, $rootScope, $stateParams, entity, Participante, Proyecto, Contacto, ParticipanteDescripcion) {
        var vm = this;

        vm.participante = entity;

        var unsubscribe = $rootScope.$on('cumminsApp:participanteUpdate', function(event, result) {
            vm.participante = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
