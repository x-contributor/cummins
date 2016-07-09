'use strict';

describe('Controller Tests', function() {

    describe('Respuesta Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockRespuesta, MockProyecto;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockRespuesta = jasmine.createSpy('MockRespuesta');
            MockProyecto = jasmine.createSpy('MockProyecto');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Respuesta': MockRespuesta,
                'Proyecto': MockProyecto
            };
            createController = function() {
                $injector.get('$controller')("RespuestaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cumminsApp:respuestaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
