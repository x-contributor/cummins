'use strict';

describe('Controller Tests', function() {

    describe('VoluntarioVisitador Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockVoluntarioVisitador, MockProyecto;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockVoluntarioVisitador = jasmine.createSpy('MockVoluntarioVisitador');
            MockProyecto = jasmine.createSpy('MockProyecto');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'VoluntarioVisitador': MockVoluntarioVisitador,
                'Proyecto': MockProyecto
            };
            createController = function() {
                $injector.get('$controller')("VoluntarioVisitadorDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cumminsApp:voluntarioVisitadorUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
