/*
 The MIT License (MIT)
 
 Copyright (c) 2015 Los Andes University
 
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 
 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.
 
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */
(function (ng) {
    var mod = ng.module('carModule', ['ngCrud', 'ui.router']);

    mod.constant('carModel', {
        name: 'car',
        displayName: 'Car',
        url: 'car',
        fields: {
            name: {
                displayName: 'Name',
                type: 'String',
                required: true
            },
            image: {
                displayName: 'Image',
                type: 'Imagen',
                required: true
            },
            price: {
                displayName: 'Price',
                type: 'Long',
                required: true
            },
            revisions: {
                displayName: 'Revisions',
                type: 'Long',
                required: true
            },
            warranty: {
                displayName: 'Warranty',
                type: 'Long',
                required: true
            },
            category: {
                displayName: 'Category',
                type: 'Reference',
                model: 'categoryModel',
                options: [],
                required: true
            }}
    });

    mod.config(['$stateProvider',
        function ($sp) {
            var basePath = 'src/modules/car/';
            var baseInstancePath = basePath + 'instance/';

            $sp.state('car', {
                url: '/car?page&limit',
                abstract: true,
                parent: 'brandDetail',
                views: {
                    brandChieldView: {
                        templateUrl: basePath + 'car.tpl.html',
                        controller: 'carCtrl'
                    }
                },
                resolve: {
                    references: ['$q', 'Restangular', function ($q, r) {
                            return $q.all({
                                category: r.all('categorys').getList()
                            });
                        }],
                    model: 'carModel',
                    cars: ['brand', '$stateParams', 'model', function (brand, $params, model) {
                            return brand.getList(model.url, $params);
                        }]}
            });
            $sp.state('carList', {
                url: '/list',
                parent: 'car',
                views: {
                    'brandInstanceView@brandInstance': {
                        templateUrl: basePath + 'list/car.list.tpl.html',
                        controller: 'carListCtrl',
                        controllerAs: 'ctrl'
                    }
                },
                resolve: {
                    model: 'carModel'
                },
                ncyBreadcrumb: {
                    label: 'car'
                }
            });
            $sp.state('carNew', {
                url: '/new',
                parent: 'car',
                views: {
                    'brandInstanceView@brandInstance': {
                        templateUrl: basePath + 'new/car.new.tpl.html',
                        controller: 'carNewCtrl',
                        controllerAs: 'ctrl'
                    }
                },
                resolve: {
                    model: 'carModel'
                },
                ncyBreadcrumb: {
                    parent: 'carList',
                    label: 'new'
                }
            });
            $sp.state('carInstance', {
                url: '/{carId:int}',
                abstract: true,
                parent: 'car',
                views: {
                    'brandInstanceView@brandInstance': {
                        template: '<div ui-view="carInstanceView"></div>'
                    }
                },
                resolve: {
                    car: ['cars', '$stateParams', function (cars, $params) {
                            return cars.get($params.carId);
                        }]
                }
            });
            $sp.state('carDetail', {
                url: '/details',
                parent: 'carInstance',
                views: {
                    carInstanceView: {
                        templateUrl: baseInstancePath + 'detail/car.detail.tpl.html',
                        controller: 'carDetailCtrl'
                    }
                },
                resolve: {
                    model: 'carModel'
                },
                ncyBreadcrumb: {
                    parent: 'carList',
                    label: 'details'
                }
            });
            $sp.state('carEdit', {
                url: '/edit',
                sticky: true,
                parent: 'carInstance',
                views: {
                    carInstanceView: {
                        templateUrl: baseInstancePath + 'edit/car.edit.tpl.html',
                        controller: 'carEditCtrl',
                        controllerAs: 'ctrl'
                    }
                },
                resolve: {
                    model: 'carModel'
                },
                ncyBreadcrumb: {
                    parent: 'carDetail',
                    label: 'edit'
                }
            });
            $sp.state('carDelete', {
                url: '/delete',
                parent: 'carInstance',
                views: {
                    carInstanceView: {
                        templateUrl: baseInstancePath + 'delete/car.delete.tpl.html',
                        controller: 'carDeleteCtrl',
                        controllerAs: 'ctrl'
                    }
                },
                resolve: {
                    model: 'carModel'
                }
            });
        }]);
})(window.angular);
