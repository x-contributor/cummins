(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .controller('ParticipanteDescripcionDetailController', ParticipanteDescripcionDetailController);

    ParticipanteDescripcionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'DataUtils', 'entity', 'ParticipanteDescripcion', 'Participante'];

    function ParticipanteDescripcionDetailController($scope, $rootScope, $stateParams, DataUtils, entity, ParticipanteDescripcion, Participante) {
        var vm = this;

        vm.participanteDescripcion = entity;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('cumminsApp:participanteDescripcionUpdate', function(event, result) {
            vm.participanteDescripcion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
