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
			
			echo "api call";
		}

		function validate_user(){

			$username=$this->input->get('textinput10');
			$password=$this->input->get('textinput11');
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
			echo $res->user_id; 
			$id=$res->user_id;
			$data['user_id']=$id;
			$vehicles=$this->fetch_user_vehicles($data);

		}

		function insert_user(){
			echo "api call input_user";
			$username=$this->input->get('username');
			echo $username;
		
			echo "api call input_user";
		}
	}

 ?>