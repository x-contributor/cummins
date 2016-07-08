'use strict';

describe('Controller Tests', function() {

    describe('Contacto Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockContacto, MockParticipante;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockContacto = jasmine.createSpy('MockContacto');
            MockParticipante = jasmine.createSpy('MockParticipante');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Contacto': MockContacto,
                'Participante': MockParticipante
            };
            createController = function() {
                $injector.get('$controller')("ContactoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cumminsApp:contactoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
