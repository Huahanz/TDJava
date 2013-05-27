package Wrapper;

/**
 * 
 * Read the data from php queue, and wrap into obj can be used by work. 
 * 
 * 
 * Income format from php :
 * map_queue maintain a list of all pvp_ids.
 * each pvp_queue has an independent queue, with the format as {{$type0, $ball_data_map}, {$type1, $ball_data_map}, {$type0, $ball_data_map} ..}
 * where $ball_data_map has format as  $pvp_ball_id => ($forward_field_name => $ball_forward_field_value).
 *         $forward_field_name is field_id need to forward, like type_id(ball_type_id)
 *         $ball_forward_field_value is the ball->$forward_field_name
 * private static $pvp_queue_key_prefix = 'pvp_forward_queue_';
 * private static $map_queue_key_prefix = 'map_forward_queue_';
 * 
 * 
 */
public class InWrapper
{
	public InWrapper()
	{
		
	}
	
}