<template>
  <div>
       <b-col cols="12" class="mt-2 mb-2">
            <b-button-toolbar>
                      <b-button-group  class="mr-2">
                        <b-button size="sm" variant="primary" @click="saveModel" >更新模型</b-button>
                      </b-button-group>
            </b-button-toolbar>
      </b-col>
       <b-col cols="12" class="mt-2 mb-2">
        <canvas ref="sceneCanvas" class="sceneCanvas"></canvas>
      </b-col >
  </div>
</template>

<script>
import * as THREE from 'three';
import {OrbitControls} from 'three/examples/jsm/controls/OrbitControls.js';
import {OBJLoader} from 'three/examples/jsm/loaders/OBJLoader.js';

export default {
  components: {  },
  props:{
    form:Object
  },
  name:"scene3d",
  data(){
    return {
       scene:null
    }
  },
  methods:{
    saveModel(){
      
    }
  },
  mounted(){
    console.log(this.$refs.sceneCanvas)
    console.log("挂载")
    var container=this.$refs.sceneCanvas;
    const renderer = new THREE.WebGLRenderer({
      canvas: container
    });


    renderer.setSize(  container.clientWidth, container.clientHeight );

    const camera = new THREE.PerspectiveCamera( 75,  2, 0.9, 100 );
    camera.position.set(0, 100, 0);
    camera.up.set(0, 10, 10);
    camera.lookAt(0, 10, 10);
    camera.position.z = 2;


    
    const controls = new OrbitControls(camera, container);
    controls.target.set(0, 5, 0);
    controls.update();


    const scene = new THREE.Scene();
  {
    const color = 0xFFFFFF;
    const intensity =1;
    const light = new THREE.SpotLight();

    light.position.set( 100, 100, 100 );

    light.castShadow = true;

    scene.add(light);
    scene.background=0x060818
  }

  const objects = [];

  const radius = 2;
  const widthSegments = 60;
  const heightSegments = 60;
  const sphereGeometry = new THREE.SphereGeometry(
      radius, widthSegments, heightSegments);



  const solarSystem = new THREE.Object3D();
  scene.add(solarSystem);
  objects.push(solarSystem);

  


  const sunMaterial = new THREE.MeshPhongMaterial({color: 0x2233FF, emissive: 0x112244});

  const sunMesh = new THREE.Mesh(sphereGeometry, sunMaterial);


  const objLoader = new OBJLoader();

  objLoader.load('m/kei-yuen-bicycle-2.obj', (root) => {
    console.log("加载完成")
    console.log(root)
    scene.add(root);

      const box = new THREE.Box3().setFromObject(root);

      const boxSize = box.getSize(new THREE.Vector3()).length();
      const boxCenter = box.getCenter(new THREE.Vector3());

      // set the camera to frame the box
      frameArea(boxSize * 2, boxSize, boxCenter, camera);
      
      // update the Trackball controls to handle the new size
      controls.maxDistance = boxSize * 10;
      controls.target.copy(boxCenter);
      controls.update();
  });


  sunMesh.scale.set(5, 5, 5);
  solarSystem.add(sunMesh);
  objects.push(sunMesh);

  const earthMaterial = new THREE.MeshPhongMaterial({color: 0x2233FF, emissive: 0x112244});
  const earthMesh = new THREE.Mesh(sphereGeometry, earthMaterial);
  earthMesh.position.x =15;

  solarSystem.add(earthMesh)
  objects.push(earthMesh);

  function resizeRendererToDisplaySize(renderer) {
    const canvas = renderer.domElement;
    const width = canvas.clientWidth;
    const height = canvas.clientHeight;
    const needResize = canvas.width !== width || canvas.height !== height;
    if (needResize) {
      renderer.setSize(width, height, false);
    }
    return needResize;
  }

  function render(time) {

    time *= 0.001;

    if (resizeRendererToDisplaySize(renderer)) {
      const canvas = renderer.domElement;
      camera.aspect = canvas.clientWidth / canvas.clientHeight;
      camera.updateProjectionMatrix();
    }


    objects.forEach((obj) => {
      // obj.rotation.y = time;
      // obj.rotation.x = time;
      obj.rotation.z = time;
    });

    renderer.render(scene, camera);

    requestAnimationFrame(render);
  }

  requestAnimationFrame(render);
  }
}
</script>

<style scoped>
.sceneCanvas {
  width: 100%;
  height: 400px;
}
</style>