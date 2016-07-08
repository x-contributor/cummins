(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .controller('VoluntarioVisitadorController', VoluntarioVisitadorController);

    VoluntarioVisitadorController.$inject = ['$scope', '$state', 'VoluntarioVisitador'];

    function VoluntarioVisitadorController ($scope, $state, VoluntarioVisitador) {
        var vm = this;
        
        vm.voluntarioVisitadors = [];

        loadAll();

        function loadAll() {
            VoluntarioVisitador.query(function(result) {
                vm.voluntarioVisitadors = result;
            });
        }
    }
})();
