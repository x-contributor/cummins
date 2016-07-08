(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .controller('ParticipanteController', ParticipanteController);

    ParticipanteController.$inject = ['$scope', '$state', 'Participante'];

    function ParticipanteController ($scope, $state, Participante) {
        var vm = this;
        
        vm.participantes = [];

        loadAll();

        function loadAll() {
            Participante.query(function(result) {
                vm.participantes = result;
            });
        }
    }
})();
