(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .controller('ContactoDetailController', ContactoDetailController);

    ContactoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Contacto', 'Participante'];

    function ContactoDetailController($scope, $rootScope, $stateParams, entity, Contacto, Participante) {
        var vm = this;

        vm.contacto = entity;

        var unsubscribe = $rootScope.$on('cumminsApp:contactoUpdate', function(event, result) {
            vm.contacto = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
