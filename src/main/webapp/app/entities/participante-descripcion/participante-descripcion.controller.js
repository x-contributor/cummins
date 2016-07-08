(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .controller('ParticipanteDescripcionController', ParticipanteDescripcionController);

    ParticipanteDescripcionController.$inject = ['$scope', '$state', 'DataUtils', 'ParticipanteDescripcion'];

    function ParticipanteDescripcionController ($scope, $state, DataUtils, ParticipanteDescripcion) {
        var vm = this;
        
        vm.participanteDescripcions = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            ParticipanteDescripcion.query(function(result) {
                vm.participanteDescripcions = result;
            });
        }
    }
})();
