<?php
defined('BASEPATH') OR exit('No direct script access allowed');

	/**
	* 
	*/
	class Api extends CI_Controller
	{
		
		function __construct()
		{
			parent::__construct();
			$this->load->model('lisd_model');
		}

		function index(){
			ini_set('memory_limit', '1024M');
			echo "api call";
		}

		function validate_user(){

			$username=$this->input->post('username');
			$password=$this->input->post('password');
			//echo ($username);
			//echo("hi");
			//echo ($password);
			//echo("hi");
			$data['user_name']=$username;

			$data['user_password']=$password;
			$res=$this->lisd_model->validate_user($data);

			if(sizeof($res)>0)
			{
				foreach ($res as $item ) {
					$ret['sessionId']=$item['user_id'];
					$desc['description']="login successful";
					$ret['message']=$desc;
					echo json_encode($ret);
				}
			
			}
			else{
			$desc['description']="login unsuccessful";
			$ret['message']=$desc;
			echo json_encode($ret);	
			}

		}


		function fetch_user_vehicle(){

			$user=$this->input->get('_USER_NAME');
			$data['user_name']=$user;
			$res=$this->lisd_model->fetch_by_username($data);
			//echo $res->user_id; 
			$id=$res->user_id;
			$inp['user_id']=$id;
			$vehicles=$this->lisd_model->fetch_user_vehicles($inp);

			$out=array();
			echo json_encode($vehicles);

		}
		function fetch_user_vehicle_id(){

			$user=$this->input->get('user_id');
			
			$inp['user_id']=$user;
			$vehicles=$this->lisd_model->fetch_user_vehicles($inp);

			$out=array();
			echo json_encode($vehicles);

		}

		function insert_user(){
			echo "hi";
			/*$stream_clean = $this->security->xss_clean($this->input->raw_input_stream);
			$request = json_decode($stream_clean);
			echo $request;
			//$form=json_decode((string)$request);
			echo $stream_clean;
			//$obj=json_decode($input_data);
			print_r( $form);
			*/
			$username=$this->input->get('first_name');
			$lastname=$this->input->get('last_name');
			$password=$this->input->get('password');
			$confpass=$this->input->get('confirm_password');
			$email=$this->input->get('email_id');
			$contact=$this->input->get('contact_no');
			echo  ($email);
			
			if($confpass==$password){

			$info['user_name']=$username;
			$info['user_lastname']=$lastname;
			$info['user_password']=$password;
			$info['user_email']=$email;
			$info['user_mobile']=$contact;
			
			$res=$this->lisd_model->insert_user($info);
			if($res==true){
				$desc['description']="registration successful";
				$ret['message']=$desc;
				echo json_encode($ret);
			}
			}
			else{
				$desc['description']="registration unsuccessful";
					$ret['message']=$desc;
					echo json_encode($ret);
			}
		}


		function insert_vehicle(){

			$vehicle_type=$this->input->get('vehicle_type');
			$vehicle_count=$this->input->get('vehicle_count');
			$immobilize=0;

			$info['vehicle_type']=$vehicle_type;
			$info['vehicle_count']=$vehicle_count;
			$info['immobilize']=$immobilize;

			$res=$this->insert_vehicle($info);
			if($res==true){
				$desc['description']="registration successful";
				$ret['message']=$desc;
				echo json_encode($ret);
			}
			else{
				$desc['description']="registration unsuccessful";
					$ret['message']=$desc;
					echo json_encode($ret);
			}

		}


		function insert_trip(){

			$user_id=$this->input->get('user_id');
			$vehicle_id=$this->input->get('vehicle_id');
			$latitude=$this->input->get('latitude');
			$longitude=$this->input->get('longitude');
			$timestamp=$this->input->get('timestamp');

			$info['user_id']=$user_id;
			$info['vehicle_id']=$vehicle_id;
			$info['latitude']=$latitude;
			$info['longitude']=$longitude;
			$info['timestamp']=$timestamp;

			$res=$this->lisd_model->insert_gps_start($info);
			if($res>0){
				$ret['trip_id']=$res;
				echo json_encode($ret);
			}
			else{
				$desc['description']="insert failed";
					$ret['message']=$desc;
					echo json_encode($ret);
			}


		}

		function update_trip(){

			$trip_id=$this->input->get('trip_id');
			$user_id=$this->input->get('user_id');
			$vehicle_id=$this->input->get('vehicle_id');
			$latitude=$this->input->get('latitude');
			$longitude=$this->input->get('longitude');
			$timestamp=$this->input->get('timestamp');
			$is_trip_live=$this->input->get('is_trip_live');

			$info['trip_id']=$trip_id;
			$info['user_id']=$user_id;
			$info['vehicle_id']=$vehicle_id;
			$info['latitude']=$latitude;
			$info['longitude']=$longitude;
			$info['timestamp']=$timestamp;
			$info['is_trip_live']=$is_trip_live;

			$res=$this->lisd_model->insert_gps_details($info);
			if($res>0){
				$ret['message']="successful insert";
				echo json_encode($ret);
			}
			else{
				$desc['description']="insert failed";
					$ret['message']=$desc;
					echo json_encode($ret);
			}


		}

		function get_flag_by_id(){
			$vehicle_id=$this->input->get('vehicle_id');
			$info['vehicle_id']=$vehicle_id;
			$res=$this->lisd_model->fetch_flag_by_id($info);
			$ret['immobilize']=$res->immobilize;
			echo json_encode($ret);
		}

		function set_flag_by_id(){
			$vehicle_id=$this->input->get('vehicle_id');
			$info['vehicle_id']=$vehicle_id;
			$res=$this->lisd_model->set_flag_by_id($info);
			$ret['message']=$res;
			echo json_encode($ret);
		}

		function carbon_footprint_generate(){

			$ch = curl_init();
			$vehicle_id=$this->input->get('vehicle_id');
			$info['vehicle_id']=$vehicle_id;

			$ret=$this->lisd_model->get_latlong($info);

			$ch = curl_init();
			curl_setopt($ch, CURLOPT_URL, "http://api.commutegreener.com/api/co2/emissions?startLat=".$ret['startlat']."&startLng=".$ret['startlong']."&endLat=".$ret['endlat']."&endLng=".$ret['endlong']."&format=json");
			// define options
			

			// apply those options
			curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);

			// execute request and get response
			$output = curl_exec($ch);
			// also get the error and response code
			curl_close($ch);
			echo($output);
		}

		
		function fetch_gps_user_id(){

			$user=$this->input->get('user_id');
			
			$inp['user_id']=$user;
			$locations=$this->lisd_model->fetch_gps_user($inp);
			$out=array();
			foreach ($locations as $item ) {
				$item['pic_image']='https://www.kinetise.com/xml/assets/pin.png';
				$out=$item+$out;
			}
			echo json_encode($out);

		}

	}

 ?>