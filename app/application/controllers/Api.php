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
			echo $res->user_id; 
			$id=$res->user_id;
			$inp['user_id']=$id;
			$vehicles=$this->lisd_model->fetch_user_vehicles($inp);

			$out=array();
			echo json_encode($vehicles);

		}

		function insert_user(){
			$stream_clean = $this->security->xss_clean($this->input->raw_input_stream);
			$request = json_decode($stream_clean);
			$formjson = $request->form;
			$form=json_decode($formjson);
			echo $stream_clean;
			$obj=json_decode($input_data);
			echo $obj['form'];
			
			$username=$form['first_name'];
			$lastname=$form['last_name'];
			$password=$form['password'];
			$confpass=$form['confirm_password'];
			$email=$from['email_id'];
			$contact=$form['contact_no'];
			echo  ($email);

			if($confpass==$password){
			echo ('hihihihihihihi');
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
	}

 ?>