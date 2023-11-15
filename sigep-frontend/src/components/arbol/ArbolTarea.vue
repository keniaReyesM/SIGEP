<template>
    <div>
        <loader :loader="enviando" />

        <div class="main-card mb-3 card mt-6">

            <div class="card-header" style="text-align: right;">
                <v-row align="center" justify="center">

                    <v-col cols="12" sm="12" md="10">
                        <v-text-field v-model.trim="busqueda" append-icon="mdi-magnify" label="Búsqueda..." outlined
                            dense hide-details class="mx-5 mt-3"></v-text-field>
                    </v-col>
                </v-row>
            </div>

            <div class="card-body">
                <div name="calendario">
                    <vue-tree-list @click="onClick" @change-name="onChangeName" @delete-node="onDel"
                        @add-node="onAddNode" :model="data" default-tree-node-name="nueva tarea"
                        default-leaf-node-name="nueva tarea" v-bind:default-expanded="false">
                        <template v-slot:leafNameDisplay="slotProps">
                            <span>
                                {{ slotProps.model.name }} <span class="muted">#{{ slotProps.model.id }}</span>
                            </span>
                        </template>
                        <span class="icon" slot="addTreeNodeIcon">📂</span>
                        <span class="icon" slot="addLeafNodeIcon">＋</span>
                        <span class="icon" slot="editNodeIcon">📃</span>
                        <span class="icon" slot="delNodeIcon">✂️</span>
                        <span class="icon" slot="leafNodeIcon">🍃</span>
                        <span class="icon" slot="treeNodeIcon">🌲</span>
                    </vue-tree-list>
                </div>

            </div>
            <div class="card-footer">
                <v-row align="center" justify="center">

                    <v-col cols="12" sm="12" md="2" class="text-center justify-center">
                        <v-btn :disabled="enviando" text outlined rounded color="success" class="mt-3">
                            Generar tablero <v-icon right> mdi-download </v-icon>
                        </v-btn>
                    </v-col>
                </v-row>
                <div class="text-right mb-4">
                    <strong> Total: {{ total }}</strong>
                </div>
            </div>
        </div>

    </div>
</template>
    
<script>
import Loader from "@/components/base/loader/Loader.vue";
import TareaService from "@/core/services/TareaService";
import Utileria from "@/core/util/Utileria";
// import vueTreeList from 'vue-tree-list';
import { VueTreeList, Tree, TreeNode } from 'vue-tree-list'

export default {
    name: "ArbolTarea",
    props: {
    },
    components: {
        Loader,
        VueTreeList
    },
    data() {
        return {
            busqueda: null,
            total: 0,
            enviando: false,
            lista: [],
            newTree: {},
            data: new Tree([
                {
                    name: 'Tarea 1',
                    id: 1,
                    pid: 0,
                    dragDisabled: true,
                    addTreeNodeDisabled: true,
                    addLeafNodeDisabled: true,
                    editNodeDisabled: true,
                    delNodeDisabled: true,
                    children: [
                        {
                            name: 'Tarea 1-2',
                            id: 2,
                            isLeaf: true,
                            pid: 1
                        }
                    ]
                },
                {
                    name: 'Tarea 2',
                    id: 3,
                    pid: 0,
                    disabled: true
                },
                {
                    name: 'Tarea 3',
                    id: 4,
                    pid: 0
                }
            ])
        }
    },
    mounted() {
        this.listarTareas();
    },
    methods: {
        listarTareas() {
            if (!this.enviando) {

                this.enviando = true;
                let parametros = {
                    busqueda: this.busqueda
                };

                TareaService.listarTareasArbol(parametros).then((res) => {

                    let { elementos, total } = res.data;
                    // this.lista = elementos;
                    this.lista = [{ "id": 0, "name": "Tareas", "children": this.construirTareasArbol(elementos) }];
                    this.total = total;

                }).catch((e) => {
                    this.$utileria.errorhttp(e);
                }).finally(() => {
                    this.enviando = false;
                });
            }
        },
        construirTareasArbol(elementos) {
            return elementos.map((tarea) => {
                return {
                    id: tarea.idTarea,
                    name: this.concatenarNombre(tarea),
                    children: this.construirTareasArbol(tarea.subtareas)
                }

            });
        },
        concatenarNombre(tarea) {
            return `${tarea.nombreProyecto}/${tarea.claveProyecto} - ${tarea.nombre}/${tarea.clave}`;
        },
        tareaSeleccionada(tareaSeleccionada) {
            console.log(tareaSeleccionada);
        },
        onDel(node) {
            console.log(node)
            node.remove()
        },

        onChangeName(params) {
            console.log(params)
        },

        onAddNode(params) {
            console.log(params)
        },

        onClick(params) {
            console.log(params)
        },

        addNode() {
            var node = new TreeNode({ name: 'nueva tarea', isLeaf: false })
            if (!this.data.children) this.data.children = []
            this.data.addChildren(node)
        },

        getNewTree() {
            var vm = this
            function _dfs(oldNode) {
                var newNode = {}

                for (var k in oldNode) {
                    if (k !== 'children' && k !== 'parent') {
                        newNode[k] = oldNode[k]
                    }
                }

                if (oldNode.children && oldNode.children.length > 0) {
                    newNode.children = []
                    for (var i = 0, len = oldNode.children.length; i < len; i++) {
                        newNode.children.push(_dfs(oldNode.children[i]))
                    }
                }
                return newNode
            }

            vm.newTree = _dfs(vm.data)
        }
    }
};
</script>

